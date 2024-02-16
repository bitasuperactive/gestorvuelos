package com.mycompany.gestorvuelos.igu.logic;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.awt.Component;
import java.lang.reflect.Field;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Aplica la mutación del documento solo en caso de no superar el límite de 
 * caracteres del atributo a modificar, determinado por las etiquetas @Size y @Digits.
 * @see jakarta.validation.constraints.Size
 * @see jakarta.validation.constraints.Digits
 */
public class MaxCharsDocumentFilter extends DocumentFilter
{
    private Validator validator;
    private Component parent;
    private Class<?> dtoClass;
    private String attrName;

    /**
     * Aplica la mutación del documento solo en caso de no superar el límite de caracteres del atributo
     * vinculado, determinado por las etiquetas @Size y @Digits.
     * @param parent Componente en el que el diálogo de error se muestra.
     * @param dtoClass Clase a la que pertenece el objeto cuyo atributo se va a modificar a través del documento.
     * @param attrName Nombre del atributo vinculado al documento que contiene 
     * la etiqueta @Size(max) o la etiqueta @Digits(integer).
     * @see jakarta.validation.constraints.Size
     * @see jakarta.validation.constraints.Digits
     */
    public MaxCharsDocumentFilter(Component parent, Class<?> dtoClass, String attrName)
    {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.parent = parent;
        this.dtoClass = dtoClass;
        this.attrName = attrName;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
    {
        // Obtenemos el texto completo antes y después del área a reemplazar.
        String textBeforeReplacement = fb.getDocument().getText(0, offset);
        String textAfterReplacement = fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length);

        // Concatenamos los textos obtenidos con el texto que se va a insertar.
        String fullReplacementText = textBeforeReplacement + text + textAfterReplacement;

        // Calculamos la longitud de la cadena a insertar.
        int replaceLength = fullReplacementText.length();

        // Obtenemos la longitud máxima del atributo que se intenta modificar.
        int maxLength = getAttrMaxLength();

        // Verificamos si se excede el límite de caracteres.
        if (replaceLength > maxLength) {
            // No se realiza la inserción.
            String message = String.format("El campo \"%s\" está limitado a %d caracteres.", attrName, maxLength);
            JOptionPane.showMessageDialog(parent, message, parent.getName(), JOptionPane.WARNING_MESSAGE);
            return;
        }

        super.replace(fb, offset, length, text, attrs);
    }
    
    /**
     * Obtiene la propiedad max de la etiqueta @Size o la propiedad integer
     * de la etiqueta @Digits acoplada al atributo para obtener su longitud máxima
     * aceptada.
     * @return Máximo de caracteres aceptado por el atributo.
     * @throws IllegalStateException El nombre del atributo no existe en el DTO 
     * o el atributo carece de las etiquetas @Size(max) y @Digits.
     */
    private int getAttrMaxLength() throws IllegalStateException
    {
        try {
            // Obtenemos el campo correspondiente al atributo en el objeto DTO.
            Field field = dtoClass.getDeclaredField(attrName);
            
            // Obtenemos las anotaciones, solo una de ellas podrá estar presente.
            Size sizeAnnotation = field.getDeclaredAnnotation(Size.class);
            Digits digitsAnnotation = field.getDeclaredAnnotation(Digits.class);
            
            // Verificamos si algunas de las anotaciones está presente.
            if (sizeAnnotation != null) {
                return sizeAnnotation.max();
            } else if (digitsAnnotation != null) {
                return digitsAnnotation.integer();
            } else {
                throw new IllegalStateException("El atributo carece de las etiquetas @Size(max) y @Digits.");
            }
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("El nombre del atributo no existe en el DTO.", e);
        }
    }
}
