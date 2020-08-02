package com.zjmzxfzhl.common.core.validator;

import com.zjmzxfzhl.common.core.validator.constraints.LengthForUtf8;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class LengthForUtf8Validator implements ConstraintValidator<LengthForUtf8, String> {

    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final String CHARSET_NAME = "UTF-8";

    private int min;
    private int max;

    @Override
    public void initialize(LengthForUtf8 parameters) {
        min = parameters.min();
        max = parameters.max();
        validateParameters();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        int length = 0;
        try {
            length = value.getBytes(CHARSET_NAME).length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        return length >= min && length <= max;
    }

    private void validateParameters() {
        if (min < 0) {
            throw LOG.getMinCannotBeNegativeException();
        }
        if (max < 0) {
            throw LOG.getMaxCannotBeNegativeException();
        }
        if (max < min) {
            throw LOG.getLengthCannotBeNegativeException();
        }
    }
}
