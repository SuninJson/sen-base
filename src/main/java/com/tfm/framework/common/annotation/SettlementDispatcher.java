package com.tfm.framework.common.annotation;


import com.tfm.framework.common.constant.BusinessType;
import com.tfm.framework.common.constant.DataHandleType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Evan Huang
 * @date 2019/5/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface SettlementDispatcher {

    /**
     * 多个市场代码 例如：SH、SZ、IB
     */
    String[] marketCodes() default {""};

    /**
     * 业务类型 例如：现券买卖、债券回购、兑付兑息等
     */
    BusinessType[] businessTypes() default {BusinessType.ALL};

    /**
     * 数据操作类型 例如：成交核对、成交核对异常处理、待交收数据生成等
     */
    DataHandleType[] dataHandleTypes() default {DataHandleType.ALL};
}
