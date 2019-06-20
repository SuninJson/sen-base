package com.sen.framework.common.service.factory;


import com.sen.framework.common.annotation.SettlementAutowired;
import com.sen.framework.common.annotation.SettlementDispatcher;
import com.sen.framework.common.annotation.SettlementFirstHandler;
import com.sen.framework.common.constant.BusinessType;
import com.sen.framework.common.constant.DataHandleType;
import com.sen.framework.common.exception.SystemException;
import com.sen.framework.common.service.configuration.SettlementConfiguration;
import com.sen.framework.common.service.dispatcher.IDispatcher;
import com.sen.framework.common.service.handler.BaseSettlementHandler;
import com.sen.framework.common.util.ClassUtil;
import com.sen.framework.common.util.ExceptionConst;
import com.sen.framework.common.util.SpringContextUtils;
import com.sen.framework.common.util.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Evan Huang
 * @date 2019/5/21
 */
public class SettlementHandlerBeanFactory<T> {
    private static final Logger logger = LoggerFactory.getLogger(SettlementHandlerBeanFactory.class);
    public static final List<BaseSettlementHandler> firstHandlers = new ArrayList<>();
    public static final String SCAN_PACKAGE = "com.iquantex.qow.service";

    static {
        List<Class<?>> firstHandlers = ClassUtil.getAllClassByAnnotation(SCAN_PACKAGE, SettlementFirstHandler.class);
        for (Class<?> firstHandlerCls : firstHandlers) {
            BaseSettlementHandler handler = initSettlementHandler((Class<? extends BaseSettlementHandler>) firstHandlerCls);
            injectAutowiredFields(handler);
            SettlementHandlerBeanFactory.firstHandlers.add(handler);
        }
    }

    public static void injectAutowiredFields(BaseSettlementHandler handler) {
        Class<?> firstHandlerCls = handler.getClass();
        Field[] fields = firstHandlerCls.getDeclaredFields();
        Class<?> fieldRelevantCls = firstHandlerCls.getSuperclass();
        while (fieldRelevantCls != null) {
            fields = ArrayUtils.addAll(fields, fieldRelevantCls.getDeclaredFields());
            fieldRelevantCls = fieldRelevantCls.getSuperclass();
        }
        for (Field field : fields) {
            if (field.getAnnotation(SettlementAutowired.class) != null) {
                Class<?> fieldType = field.getType();
                String fieldTypeName = fieldType.getSimpleName();
                char[] fieldTypeNameChars = fieldTypeName.toCharArray();
                String springBeanName;
                if (fieldTypeNameChars[0] == 'V' && StringUtils.isUpperCase(fieldTypeNameChars[1])) {
                    springBeanName = fieldTypeName;
                } else {
                    springBeanName = StringUtils.lowerFirst(fieldTypeName);
                    if (fieldType.isInterface() && springBeanName.contains("Service")) {
                        springBeanName = springBeanName + "Impl";
                    }
                }
                field.setAccessible(true);
                try {
                    field.set(handler, SpringContextUtils.getBean(springBeanName));
                } catch (IllegalAccessException e) {
                    throw new SystemException(String.format("<%s>注入<%s>失败：\n%s", firstHandlerCls.getSimpleName(), springBeanName, ExceptionConst.printStackTrace(e)));
                }
            }
        }
    }

    /**
     * 找到委派者的所有执行链中的第一个处理者
     *
     * @param dispatcherCls 委派者的Class
     * @return 所有相关的第一个处理者
     */
    public static List<BaseSettlementHandler> getFirstHandlersOfDispatcher(Class<? extends IDispatcher> dispatcherCls) {
        return firstHandlers.stream().filter(h -> isRelatedToDispatcher(h, dispatcherCls)).collect(Collectors.toList());
    }

    /**
     * 判断处理者是否与委派者相关
     *
     * @param handler       结算任务处理者
     * @param dispatcherCls 结算任务委派者
     * @return true：相关   false：无关
     */
    private static boolean isRelatedToDispatcher(BaseSettlementHandler handler, Class<? extends IDispatcher> dispatcherCls) {
        boolean isRelevantMarketCode = false;
        boolean isRelevantBusinessType = false;
        boolean isRelevantDataHandleType = false;
        SettlementDispatcher dispatcherAnnotation = dispatcherCls.getAnnotation(SettlementDispatcher.class);
        String[] marketCodes = dispatcherAnnotation.marketCodes();
        BusinessType[] businessTypes = dispatcherAnnotation.businessTypes();
        DataHandleType[] dataHandleTypes = dispatcherAnnotation.dataHandleTypes();

        List<BusinessType> businessTypeLs = Arrays.asList(businessTypes);
        List<DataHandleType> dataHandleTypeLs = Arrays.asList(dataHandleTypes);

        if (Arrays.asList(marketCodes).contains(handler.getMarketCode())) {
            isRelevantMarketCode = true;
        }
        if (businessTypeLs.contains(handler.getBusinessType()) || businessTypeLs.contains(BusinessType.ALL)) {
            isRelevantBusinessType = true;
        }
        if (dataHandleTypeLs.contains(handler.getDataHandleType()) || dataHandleTypeLs.contains(DataHandleType.ALL)) {
            isRelevantDataHandleType = true;
        }
        return isRelevantMarketCode && isRelevantBusinessType && isRelevantDataHandleType;
    }

    private static BaseSettlementHandler initSettlementHandler(Class<? extends BaseSettlementHandler> handleCls) {
        return getHandlerBean(handleCls, getSettlementConfiguration(handleCls));
    }

    public static BaseSettlementHandler getHandlerBean(Class<? extends BaseSettlementHandler> handleCls, SettlementConfiguration configuration) {
        try {
            Constructor<? extends BaseSettlementHandler> constructor = handleCls.getConstructor(SettlementConfiguration.class);
            return constructor.newInstance(configuration);
        } catch (Exception e) {
            throw new SystemException(String.format("创建<%s>处理者新实例失败：\n%s", handleCls.getSimpleName(), ExceptionConst.printStackTrace(e)));
        }
    }

    public static SettlementConfiguration getSettlementConfiguration(Class<? extends BaseSettlementHandler> handleCls) {
        SettlementConfiguration configuration = new SettlementConfiguration();
        SettlementFirstHandler handlerAnnotation = handleCls.getAnnotation(SettlementFirstHandler.class);
        String marketCode = handlerAnnotation.marketCode();
        configuration.setMarketCode(marketCode);
        configuration.setBusinessType(handlerAnnotation.businessType());
        configuration.setDataHandleType(handlerAnnotation.dataHandleType());
        return configuration;
    }
}
