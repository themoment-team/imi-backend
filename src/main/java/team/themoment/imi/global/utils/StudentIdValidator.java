package team.themoment.imi.global.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StudentIdValidator implements ConstraintValidator<ValidStudentId, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true; // @NotNull과 병행 사용해야 함

        if (value < 1000 || value > 3420) return false;

        int clazz = (value / 100) % 10;
        int number = value % 100;

        return (clazz >= 1 && clazz <= 4) &&
                (number >= 1 && number <= 20);
    }
}