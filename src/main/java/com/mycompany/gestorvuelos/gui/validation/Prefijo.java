package com.mycompany.gestorvuelos.gui.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * El elemento anotado:
 * <pre>
 *  - Es obligatorio.
 *  - Debe ser un valor único en las compañías registradas.
 *  - Debe ser un valor contenido entre 0 y 999.
 * </pre>
 */
@NotNull(message = "Campo obligatorio")
@Min(value = 1, message = "Debe ser positivo mayor a 0")
@Max(value = 999, message = "No puede ser mayor a 999")
@Target(ElementType.FIELD)
@Constraint(validatedBy = { PrefijoValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Prefijo
{
    String message() default "Este prefijo ya está registrado";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
