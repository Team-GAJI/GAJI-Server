package gaji.service.domain.recruit.validation;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.recruit.annotation.ExistCategory;
import gaji.service.global.exception.code.status.GlobalErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryExistValidator implements ConstraintValidator<ExistCategory, CategoryEnum> {

    @Override
    public void initialize(ExistCategory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CategoryEnum value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        boolean isValid = isEnumValueValid(value);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(GlobalErrorStatus._INVALID_CATEGORY.getMessage()).addConstraintViolation();
        }

        return isValid;
    }

    private boolean isEnumValueValid(CategoryEnum value) {
        for (CategoryEnum category : CategoryEnum.values()) {
            if (category == value) {
                return true;
            }
        }

        return false;
    }
}