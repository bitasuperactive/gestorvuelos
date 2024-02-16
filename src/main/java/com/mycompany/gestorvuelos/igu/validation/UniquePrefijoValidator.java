package com.mycompany.gestorvuelos.igu.validation;

import com.mycompany.gestorvuelos.business.logic.ListManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Valida que el prefijo sea un valor único en las compañías registradas.
 * @see ListManager#isCompaniaPrefijoUnique(short)
 */
public class UniquePrefijoValidator implements ConstraintValidator<Unique, Short>
{

    @Override
    public boolean isValid(Short t, ConstraintValidatorContext cvc)
    {
        // No es comptencia de este validador advertir del nulo.
        if (t == null) {
            return true;
        }

        return ListManager.isCompaniaPrefijoUnique(t);
    }

}
