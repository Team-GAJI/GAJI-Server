package gaji.service.domain.common.validation;

import gaji.service.domain.common.annotation.ExistsCategory;
import gaji.service.domain.common.service.CategoryService;
import gaji.service.global.exception.code.status.GlobalErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryExistsValidator implements ConstraintValidator<ExistsCategory, List<Long>> {
    private final CategoryService categoryService;

    @Override
    public void initialize(ExistsCategory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
        boolean isValid = values.stream()
                .allMatch(value -> categoryService.existsByCategoryId(value));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(GlobalErrorStatus._INVALID_CATEGORY.getMessage()).addConstraintViolation();
        }

        return isValid;
    }
}
