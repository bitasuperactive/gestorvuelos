package com.mycompany.gestorvuelos.gui.validation.compania;

import com.mycompany.gestorvuelos.business.logic.ListManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Valida que el código sea un valor único en las compañías registradas.
 * @see ListManager#isCompaniaCodigoUnique(java.lang.String)
 */
public class CodigoValidator implements ConstraintValidator<Codigo, String>
{

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc)
    {
        // No es comptencia de este validador advertir del nulo o cadena vacía.
        if (t == null || t.isEmpty()) {
            return true;
        }

        return ListManager.isCompaniaCodigoUnique(t);
    }

}
