package software.project.customValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy =ValidateEmail.class)
@Documented


public @interface UniqueEmail {

    String message() default "must be unique email working";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
}
