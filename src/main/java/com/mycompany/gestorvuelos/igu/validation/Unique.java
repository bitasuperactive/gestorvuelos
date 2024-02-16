package com.mycompany.gestorvuelos.igu.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * El elemento anotado debe ser un valor único para el prefijo o código de las
 * compañías registradas.
 */
@Target(ElementType.FIELD)
@Constraint(validatedBy = { UniquePrefijoValidator.class, UniqueCodigoValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unique
{
    String message() default "Este identificador ya está registrado";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
