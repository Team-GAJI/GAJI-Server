package gaji.service.domain.post.validation;

import gaji.service.domain.enums.PostTypeEnum;
import gaji.service.domain.post.annotation.ExistPostType;
import gaji.service.domain.post.code.PostErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PostTypeExistValidator implements ConstraintValidator<ExistPostType, PostTypeEnum> {

    @Override
    public void initialize(ExistPostType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    // TODO: 검증 메시지 적용 안되는 문제 해결하기
    @Override
    public boolean isValid(PostTypeEnum value, ConstraintValidatorContext context) {
        boolean isValid = (value == PostTypeEnum.ROOM) || (value == PostTypeEnum.BLOG) || (value == PostTypeEnum.PROJECT) || (value == PostTypeEnum.QUESTION);
//        boolean isValid = false;
//
//        for (PostTypeEnum postTypeEnum : PostTypeEnum.values()) {
//            if (postTypeEnum.equals(value)) {
//                isValid = true;
//            }
//        }

//        boolean isValid = Arrays.stream(PostTypeEnum.values())
//                .allMatch(postTypeEnum -> postTypeEnum.equals(value));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(PostErrorStatus._INVALID_POST_TYPE.getMessage()).addConstraintViolation();
        }

        return isValid;
    }
}
