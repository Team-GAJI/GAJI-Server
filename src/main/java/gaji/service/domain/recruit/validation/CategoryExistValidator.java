package gaji.service.domain.recruit.validation;

import gaji.service.domain.enums.RecruitPostCategoryEnum;
import gaji.service.domain.recruit.annotation.ExistCategory;
import gaji.service.domain.recruit.code.RecruitErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryExistValidator implements ConstraintValidator<ExistCategory, List<RecruitPostCategoryEnum>> {

    @Override
    public void initialize(ExistCategory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<RecruitPostCategoryEnum> values, ConstraintValidatorContext context) {
        if (values == null) {
            return true; // null 값은 별도의 검증 어노테이션으로 처리
        }

        boolean isValid = values.stream()
                .allMatch(this::isEnumValueValid);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(RecruitErrorStatus._RECRUIT_CATEGORY_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;
    }

    private boolean isEnumValueValid(RecruitPostCategoryEnum value) {
        for (RecruitPostCategoryEnum category : RecruitPostCategoryEnum.values()) {
            if (category == value) {
                return true;
            }
        }

        return false;
    }
}