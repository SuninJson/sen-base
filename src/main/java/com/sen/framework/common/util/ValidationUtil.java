package com.sen.framework.common.util;

import com.sen.framework.common.service.configuration.SettlementConfiguration;
import com.sen.framework.common.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 校验工具
 *
 * @author Evan Huang
 * @date 2019/5/16
 */
public class ValidationUtil {
    private static Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T> List<String> validate(T entity) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> constraintViolation = validator.validate(entity);

        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<T> violation : constraintViolation) {
            messageList.add(violation.getMessage());
        }
        return messageList;
    }

    public static <T> void validateAndThrow(T entity) {
        List<String> exceptionMessageList = validate(entity);
        if (!CollectionUtils.isEmpty(exceptionMessageList)) {
            String exceptionMessage = String.format("实体类【%s】参数异常：%s", entity.getClass().getSimpleName(), Arrays.toString(exceptionMessageList.toArray()));
            logger.error(exceptionMessage);
            throw new SystemException(exceptionMessage);
        }
    }

    public static void main(String[] args) {
        SettlementConfiguration configuration = new SettlementConfiguration();
        validateAndThrow(configuration);
    }

}
