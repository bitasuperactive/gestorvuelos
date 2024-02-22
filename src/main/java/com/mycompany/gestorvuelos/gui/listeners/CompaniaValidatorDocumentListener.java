package com.mycompany.gestorvuelos.gui.listeners;

import com.mycompany.gestorvuelos.dto.Compania;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import com.mycompany.gestorvuelos.gui.interfaces.CompaniaValidationFormulary;
import com.mycompany.gestorvuelos.gui.interfaces.MonoChecks;

/**
 * Validador en tiempo real del input del usuario para los campos de las compañías.
 * Es un validador a nivel de atributo, utilizando el grupo MonoChecks.
 * @see MonoChecks
 */
public class CompaniaValidatorDocumentListener implements DocumentListener
{
    private Validator validator;
    private final Class<?> VALIDATION_TYPE;
    private CompaniaValidationFormulary parent;
    private String attrName;
    private JLabel violationLabel;
    private final Class<?> FIELD_TYPE;

    /**
     * Valida el atributo de la compañía vinculado al documento al actualizar 
     * su contenido.
     * @param parent Padre del componente que implimenta la clase.
     * @param attrName Nombre del atributo de la compañía vinculado al documento
     * que contiene las etiquetas de validación.
     * @param violationLabel Etiqueta en la que se mostraran los mensajes de error
     * de validación del atributo.
     * @throws IllegalArgumentException Si el atributo de la compañía no existe.
     * @see Compania
     */
    public CompaniaValidatorDocumentListener(CompaniaValidationFormulary parent, String attrName, JLabel violationLabel) throws IllegalArgumentException
    {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        VALIDATION_TYPE = MonoChecks.class;
        this.parent = parent;
        this.attrName = attrName;
        this.violationLabel = violationLabel;
        
        try {
            // Obtenemos el tipo de dato del campo.
            FIELD_TYPE = Compania.class.getDeclaredField(attrName).getType();
        } catch (NoSuchFieldException ex) {
            throw new IllegalArgumentException(
                    String.format("El campo %s no existe.", attrName), 
                    ex);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        validateValue(getDocumentText(e));
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        validateValue(getDocumentText(e));
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        
    }
    
    /**
     * Obtiene el objeto representativo del contenido del documento 
     * dependiendo del tipo de dato del campo.
     * <br> Tipos contemplados (solo wrappers): String y Short.
     * @param e Evento del documento.
     * @return Objeto repesentativo del contenido del documento.
     */
    private Object getDocumentText(DocumentEvent e)
    {
        var document = e.getDocument();
        Object value = null;
        String text = "";

        try {
            text = e.getDocument().getText(0, document.getLength());

            if (FIELD_TYPE.equals(String.class)) {
                value = text;
            } else if (FIELD_TYPE.equals(Short.class)) {
                value = Short.valueOf(text);
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            if (!text.isEmpty()) {
                // Si el valor no es nulo (""), se indica que el tipo de valor
                // no corresponde con el tipo del campo pasando el String
                // del documento.
                value = text;
            }
        }

        return value;
    }
    
    /**
     * Valida el atributo attrName de acuerdo con sus etiquetas de validación.
     * @param value Objeto a validar.
     * @throws IllegalArgumentException Si el tipo del dato no es compatible para la validación.
     * @see jakarta.validation
     */
    private void validateValue(Object value) throws IllegalArgumentException 
    {
        // Comprobamos que el tipo del objeto corresponda con el tipo del campo.
        if (value != null && !value.getClass().equals(FIELD_TYPE))
        {
            manageViolationMessage("Tipo de valor inválido.");
            return;
        }
        
        Set<ConstraintViolation<Compania>> violations;

        // Validamos el objeto en función de su tipo.
        if (value == null) {
            violations = validator.validateValue(Compania.class, attrName, null, VALIDATION_TYPE);
        } else if (value instanceof String) {
            // Se requiere nulificar las cadenas vacías para evitar intromisiones
            // entre las etiquetas @NotBlank y @Pattern.
            String str = (String) value;
            value = (str.isEmpty()) ? null : str;
            violations = validator.validateValue(Compania.class, attrName, value, VALIDATION_TYPE);
        } else if (value instanceof Short) {
            violations = validator.validateValue(Compania.class, attrName, (Short) value, VALIDATION_TYPE);
        } else {
            throw new IllegalArgumentException("El tipo del dato no es compatible para la validación.");
        }
        
        if (violations.isEmpty()) {
            manageSuccessValidation();
            return;
        }
        
        manageViolationMessage(violations.iterator().next().getMessage());
    }
    
    private void manageSuccessValidation()
    {
        violationLabel.setText("");
        parent.fieldValidationDone();
    }
    
    private void manageViolationMessage(String message)
    {
        violationLabel.setText(message);
        parent.fieldValidationDone();
    }
}
