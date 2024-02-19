package com.mycompany.gestorvuelos.gui.validation.compania;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * El elemento anotado:
 * <pre>
 *  - Es obligatorio.
 *  - Debe ser una cadena de dos caracteres mayúsculas de tamaño fijo, puede contener dos letras mayúsculas o una mayúscula y un número (en este orden).
 *  - Debe ser un valor único en las compañías registradas.
 * </pre>
 */
@NotBlank(message = "Campo obligatorio")
@Pattern(regexp = "^([A-Z]{2}|[A-Z][0-9])$",
        message = "Debe contener dos caracteres en mayúsculas o"
        + " un caracter en mayúscula y un número")
@Target(ElementType.FIELD)
@Constraint(validatedBy = { CodigoValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Codigo
{
    String message() default "Este código ya está registrado";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
