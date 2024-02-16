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
import com.mycompany.gestorvuelos.gui.interfaces.ValidationFormulary;

/**
 * Validador en tiempo real del input del usuario para los datos de las compañías.
 * <br> (!) Clase descartada al no encontrar una forma eficiente de enviar los mensajes 
 * del validador a la igu.
 */
public class CompaniaValidatorDocumentListener implements DocumentListener
{
    private Validator validator;
    private ValidationFormulary parent;
    private String attrName;
    private JLabel violationLabel;
    private Class<?> fieldType;

    /**
     * Valida el atributo vinculado al documento al actualizar su contenido.
     * @param parent Padre del componente que implimenta este listener.
     * @param attrName Nombre del atributo vinculado al documento que contiene las etiquetas de validación.
     * @param violationLabel Etiqueta en la que se mostraran los mensajes de error
     * de validación del atributo.
     * @see Compania
     */
    public CompaniaValidatorDocumentListener(ValidationFormulary parent, String attrName, JLabel violationLabel)
    {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.parent = parent;
        this.attrName = attrName;
        this.violationLabel = violationLabel;
        
        // Obtenemos el tipo de dato del campo.
        try {
            fieldType = Compania.class.getDeclaredField(attrName).getType();
        } catch (NoSuchFieldException ex) {
            fieldType = null;
            ex.printStackTrace();
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

            if (fieldType.equals(String.class)) {
                value = text;
            } else if (fieldType.equals(Short.class)) {
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
        if (value != null && !value.getClass().equals(fieldType))
        {
            manageViolationMessage("Tipo de valor inválido.");
            return;
        }
        
        Set<ConstraintViolation<Compania>> violations;

        // Validamos el objeto en función de su tipo.
        if (value == null) {
            violations = validator.validateValue(Compania.class, attrName, null);
        } else if (value instanceof String) {
            // Se requiere nulificar las cadenas vacías para evitar intromisiones
            // entre las etiquetas @NotBlank y @Pattern.
            String str = (String) value;
            value = (str.isEmpty()) ? null : str;
            violations = validator.validateValue(Compania.class, attrName, value);
        } else if (value instanceof Short) {
            violations = validator.validateValue(Compania.class, attrName, (Short) value);
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
        parent.getSubmitButton().setEnabled(true);
    }
    
    private void manageViolationMessage(String message)
    {
        violationLabel.setText(message);
        parent.getSubmitButton().setEnabled(false);
    }
}
