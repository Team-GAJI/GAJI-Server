package gaji.service.domain.room.validation.annotation;

import gaji.service.domain.room.validation.WeekValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WeekValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWeek {
    String message() default "유효하지 않은 형식의 주차입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}