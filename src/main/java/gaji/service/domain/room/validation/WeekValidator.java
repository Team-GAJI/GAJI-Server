package gaji.service.domain.room.validation;

import gaji.service.domain.room.code.RoomErrorStatus;
import gaji.service.domain.room.validation.annotation.ValidWeek;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class WeekValidator implements ConstraintValidator<ValidWeek, Integer> {


    @Override
    public void initialize(ValidWeek constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(Integer week, ConstraintValidatorContext context) {
        if (week == null) {
            return false; // null 값도 유효하지 않은 것으로 처리
        }

        if (week < 1) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(RoomErrorStatus._WEEK_NOT_VALID.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}