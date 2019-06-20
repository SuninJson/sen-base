package com.sen.framework.common.annotation;



import com.sen.framework.common.constant.BusinessType;
import com.sen.framework.common.constant.DataHandleType;
import com.sen.framework.common.service.handler.BaseSettlementHandler;
import com.sen.framework.common.service.handler.FinalSettlementHandler;

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
public @interface SettlementFirstHandler {

    /**
     * 市场代码 例如：SH、SZ、IB等
     */
    String marketCode() default "";

    /**
     * 业务类型 例如：现券买卖、债券回购、兑付兑息等
     */
    BusinessType businessType() default BusinessType.ALL;

    /**
     * 数据处理类型 例如：成交核对、异常成交处理、待交收数据生成、证券交收、资金清算、资金交收等
     */
    DataHandleType dataHandleType() default DataHandleType.ALL;

    Class<? extends BaseSettlementHandler> nextHandler() default FinalSettlementHandler.class;
}
