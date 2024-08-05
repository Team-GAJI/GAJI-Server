package gaji.service.domain.common.validation;

import gaji.service.domain.common.annotation.CheckHashtagListElement;
import gaji.service.global.exception.code.status.GlobalErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HashtagListElementValidator implements ConstraintValidator<CheckHashtagListElement, List<String>> {

    @Override
    public void initialize(CheckHashtagListElement constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        // null인 경우 유효성 검사에서 제외
        if (values == null) {
            return true;
        }

        // 리스트 안의 element중에서 하나라도 ["", " ", null]값에 해당되면 false, 아니면 true
        boolean isValid = values.stream()
                .allMatch(value -> value != null && !value.trim().isEmpty());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(GlobalErrorStatus._HASHTAG_ISBLANK.getMessage()).addConstraintViolation();
        }

        return isValid;
    }
}
