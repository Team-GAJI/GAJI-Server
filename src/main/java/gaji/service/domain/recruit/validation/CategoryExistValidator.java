package gaji.service.domain.recruit.validation;

import gaji.service.domain.enums.CategoryEnum;
import gaji.service.domain.recruit.annotation.ExistCategory;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryExistValidator implements ConstraintValidator<ExistCategory, List<CategoryEnum>> {

    @Override
    public void initialize(ExistCategory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<CategoryEnum> values, ConstraintValidatorContext context) {
        if (values == null) {
            return true;
        }

        boolean isValid = values.stream()
                .allMatch(this::isEnumValueValid);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(RecruitErrorStatus._RECRUIT_CATEGORY_NOT_FOUND.getMessage()).addConstraintViolation();
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