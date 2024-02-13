package com.mycompany.gestorvuelos.igu.logica;

import com.mycompany.gestorvuelos.dto.Compania;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * Validador en tiempo real del input del usuario para los datos de las compañías.
 * <br> (!) Clase descartada al no encontrar una forma eficiente de enviar los mensajes 
 * del validador a la igu.
 */
@Deprecated
public class CompaniaValidatorDocumentListener implements DocumentListener
{
    private Validator validator;
    private String attrName;
    private Class<?> fieldType;

    /**
     * Valida el atributo vinculado al documento al actualizar su contenido.
     * @param attrName Nombre del atributo vinculado al documento que contiene las etiquetas de validación.
     * @see Compania
     */
    public CompaniaValidatorDocumentListener(String attrName)
    {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.attrName = attrName;
        
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
     * dependiendo del tipo de dato del campo (solo wrappers).
     * <br> Tipos contemplados: String, Short, Integer y Long.
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
            } else if (fieldType.equals(Integer.class)) {
                value = Integer.valueOf(text);
            } else if (fieldType.equals(Long.class)) {
                value = Long.valueOf(text);
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
     * @throws IllegalArgumentException Si el tipo de dato no es compatible para la validación.
     * @see jakarta.validation
     */
    private void validateValue(Object value) throws IllegalArgumentException {        
        if (value != null && !value.getClass().equals(fieldType))
        {
            manageViolation(String.format("El tipo %s no es apto para el atributo %s (%s).",
                    value.getClass(), attrName, fieldType));
            return;
        }
        
        Set<ConstraintViolation<Compania>> violations;

        if (value == null) {
            violations = validator.validateValue(Compania.class, attrName, null);
        } else if (value instanceof String) {
            violations = validator.validateValue(Compania.class, attrName, (String) value);
        } else if (value instanceof Short) {
            violations = validator.validateValue(Compania.class, attrName, (Short) value);
        } else if (value instanceof Integer) {
            violations = validator.validateValue(Compania.class, attrName, (Integer) value);
        } else if (value instanceof Long) {
            violations = validator.validateValue(Compania.class, attrName, (Long) value);
        }  else {
            throw new IllegalArgumentException("El tipo de dato no es compatible para la validación.");
        }
        
        for (ConstraintViolation<Compania> violation : violations)
        {
            manageViolation(violation.getMessage());
        }
    }
    
    // TODO - Manejar violaciones de validación.
    /**
     * Imprime el mensaje correspondiente a la violación de validación.
     * @param message Mensaje generado por la violación.
     */
    private void manageViolation(String message)
    {
        System.err.println(message);
    }
}
