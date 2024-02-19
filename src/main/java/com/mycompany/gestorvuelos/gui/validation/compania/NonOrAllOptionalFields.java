package com.mycompany.gestorvuelos.gui.validation.compania;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Valida que todos los campos opcionales de la compañía ("direccionSedeCentral",
 * "municipioSedeCentral", "telefonoATA" y "telefonoATC") estén vacíos o rellenados.
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Constraint(validatedBy = { NonOrAllOptionalFieldsValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NonOrAllOptionalFields
{
    String message() default """
                             Los campos opcionales:
                                - Dirección de la sede central
                                - Municipio de la sede central
                                - Teléfono de atención al aeropuerto
                                - Teléfono de atención al cliente
                             
                             Deben estar vacíos o correctamente rellenados.
                             """;
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
