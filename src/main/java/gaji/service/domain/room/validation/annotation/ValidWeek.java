package gaji.service.domain.room.validation.annotation;

import gaji.service.domain.room.validation.WeekValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WeekValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWeek {
    String message() default "Invalid week value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}