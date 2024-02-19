package com.mycompany.gestorvuelos.gui.validation;

import com.mycompany.gestorvuelos.dto.Compania;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para la etiqueta @NonOrAllOptionalFields.
 * @see NonOrAllOptionalFields
 */
public class NonOrAllOptionalFieldsValidator implements ConstraintValidator<NonOrAllOptionalFields, Compania>
{

    @Override
    public boolean isValid(Compania t, ConstraintValidatorContext cvc)
    {
        // No es comptencia de este validador advertir del nulo.
        if (t == null) {
            return true;
        }
        
        boolean isValid = (t.getDireccionSedeCentral().isEmpty()
                && t.getMunicipioSedeCentral().isEmpty()
                && t.getTelefonoATA().isEmpty()
                && t.getTelefonoATC().isEmpty())
                || (!t.getDireccionSedeCentral().isBlank()
                && !t.getMunicipioSedeCentral().isBlank()
                && !t.getTelefonoATA().isBlank()
                && !t.getTelefonoATC().isBlank());
        
        return isValid;
    }
    
}
