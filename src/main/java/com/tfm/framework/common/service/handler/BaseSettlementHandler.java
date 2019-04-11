package com.tfm.framework.common.service.handler;


import com.tfm.framework.common.annotation.SettlementAutowired;
import com.tfm.framework.common.annotation.SettlementFirstHandler;
import com.tfm.framework.common.annotation.SettlementHandler;
import com.tfm.framework.common.bean.VFundRelevantCode;
import com.tfm.framework.common.constant.BusinessType;
import com.tfm.framework.common.constant.DataHandleType;
import com.tfm.framework.common.exception.SystemException;
import com.tfm.framework.common.service.configuration.SettlementConfiguration;
import com.tfm.framework.common.service.factory.SettlementHandlerBeanFactory;
import com.tfm.framework.common.util.ExceptionConst;
import com.tfm.framework.common.util.SpringContextUtils;
import com.tfm.framework.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @author Evan Huang
 * @date 2019/5/16
 */
public abstract class BaseSettlementHandler implements ISettlementHandler {

    public Logger logger = LoggerFactory.getLogger(this.getClass());
    private Class<? extends BaseSettlementHandler> handleCls;
    private String name;
    private ISettlementHandler nextHandler;
    private SettlementConfiguration configuration;

    public BaseSettlementHandler(SettlementConfiguration configuration) {
        handleCls = this.getClass();
        this.configuration = configuration;
        initName();
        if (handleCls != FinalSettlementHandler.class) {
            initNextHandler();
        }
        initSettlementAutowired();
    }

    private void initName() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--------结算任务委派者--------")
                .append("\n市场：").append(getMarketCode())
                .append("\n业务类型：").append(getBusinessType())
                .append("\n数据操作类型：").append(getDataHandleType());
        sb.append("\n-----------------------------\n");
        name = sb.toString();
    }

    private void initSettlementAutowired() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(SettlementAutowired.class) != null) {
                String fieldName = StringUtils.lowerFirst(field.getType().getSimpleName());
                field.setAccessible(true);
                try {
                    field.set(this, SpringContextUtils.getBean(fieldName));
                } catch (IllegalAccessException e) {
                    throw new SystemException(String.format("<%s>注入<%s>失败：\n%s", this.getClass().getSimpleName(), fieldName, ExceptionConst.printStackTrace(e)));
                }
            }
        }
    }


    private void initNextHandler() {
        try {
            Class<? extends BaseSettlementHandler> nextHandlerCls;
            SettlementHandler handlerAnnotation = handleCls.getAnnotation(SettlementHandler.class);
            if (handlerAnnotation != null) {
                nextHandlerCls = handlerAnnotation.nextHandler();
            } else {
                SettlementFirstHandler firstHandlerAnnotation = handleCls.getAnnotation(SettlementFirstHandler.class);
                nextHandlerCls = firstHandlerAnnotation.nextHandler();
            }
            if (nextHandlerCls != FinalSettlementHandler.class) {
                this.nextHandler = SettlementHandlerBeanFactory.getHandlerBean(nextHandlerCls, configuration);
            } else {
                nextHandler = setFinalSettlementHandler();
            }
        } catch (Exception e) {
            throw new SystemException(String.format("<%s>初始化下一个处理者实例失败：\n%s", handleCls.getSimpleName(), ExceptionConst.printStackTrace(e)));
        }
    }

    public ISettlementHandler setFinalSettlementHandler() {
        return new FinalSettlementHandler(configuration);
    }

    @Override
    public ISettlementHandler getNextHandler() {
        return nextHandler;
    }

    @Override
    public void setFundInfo(List<VFundRelevantCode> fundRelevantCodeList) {
        configuration.setFundRelevantCodeList(fundRelevantCodeList);
    }

    @Override
    public void setCurTradeDate(Date curTradeDate) {
        configuration.setCurTradeDate(curTradeDate);
    }

    public String getMarketCode() {
        return configuration.getMarketCode();
    }

    public BusinessType getBusinessType() {
        return configuration.getBusinessType();
    }

    public DataHandleType getDataHandleType() {
        return configuration.getDataHandleType();
    }

    public List<VFundRelevantCode> getFundInfo() {
        return configuration.getFundRelevantCodeList();
    }

    public Date getCurTradeDate() {
        return configuration.getCurTradeDate();
    }

    @Override
    public String getName() {
        return name;
    }


}
