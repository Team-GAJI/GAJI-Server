package gaji.service.domain.room.validation;

import gaji.service.domain.room.code.PostErrorStatus;
import gaji.service.domain.room.validation.annotation.ValidWeek;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class WeekValidator implements ConstraintValidator<ValidWeek, Long> {

    @Override
    public void initialize(ValidWeek constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long week, ConstraintValidatorContext context) {
        if (week == null) {
            return true; // null 값은 다른 어노테이션(@NotNull 등)으로 처리할 수 있습니다.
        }
        
        boolean isValid = week >= 1;
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(PostErrorStatus._WEEK_NOT_VALID.getMessage())
                   .addConstraintViolation();
        }
        return isValid;
    }
}