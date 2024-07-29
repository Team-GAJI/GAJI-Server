package gaji.service.domain.post.validation;

import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.annotation.ExistPostType;
import gaji.service.domain.post.code.PostErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PostTypeExistValidator implements ConstraintValidator<ExistPostType, PostTypeEnum> {

    @Override
    public void initialize(ExistPostType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PostTypeEnum value, ConstraintValidatorContext context) {
        boolean isValid = value == PostTypeEnum.BLOG || value == PostTypeEnum.PROJECT_RECRUITMENT || value == PostTypeEnum.QUESTION;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(PostErrorStatus._POST_TYPE_NOT_FOUND.getMessage()).addConstraintViolation();
        }

        return isValid;
    }
}
