package com.sen.framework.common.service.dispatcher;


import com.sen.framework.common.service.factory.SettlementHandlerBeanFactory;
import com.sen.framework.common.service.handler.BaseSettlementHandler;
import com.sen.framework.common.service.handler.ISettlementHandler;
import com.sen.framework.common.service.handler.SettlementHandlerChain;
import com.sen.framework.common.annotation.SettlementDispatcher;
import com.sen.framework.common.bean.VFundRelevantCode;
import com.sen.framework.common.constant.BusinessType;
import com.sen.framework.common.constant.DataHandleType;
import com.sen.framework.common.exception.SystemException;
import com.sen.framework.common.util.ExceptionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

/**
 * 任务委派者模板类
 *
 * @author Evan Huang
 * @date 2019/5/16
 */
public abstract class BaseDispatcher<T> implements IDispatcher<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseDispatcher.class);

    private String[] marketCodes;
    private BusinessType[] businessTypes;
    private DataHandleType[] dataHandleTypes;

    private boolean onlyHandleEffectiveFunds = false;
    private Class<? extends IDispatcher> dispatcherCls;
    private String name;
    private List<String> specificFundsList;
    private List<VFundRelevantCode> fundRelevantCodeList;
    private Map<String, Date> curTradeDateMap = new HashMap<>();
    private List<SettlementHandlerChain> handlerChains = new ArrayList<>();
    private CountDownLatch countDownLatch;
    private ExecutorService threadExecutor;
    public Map<Class<? extends ISettlementHandler>, Object> resultMap = new HashMap<>();


    /**
     * @param dispatcherCls 实际委派者的Class
     */
    public BaseDispatcher(Class<? extends IDispatcher> dispatcherCls) {
        this.dispatcherCls = dispatcherCls;
    }

    private void initStrategies() {
        dispatcherCls = this.getClass();
        SettlementDispatcher dispatcherAnnotation = dispatcherCls.getAnnotation(SettlementDispatcher.class);
        this.marketCodes = dispatcherAnnotation.marketCodes();
        this.businessTypes = dispatcherAnnotation.businessTypes();
        this.dataHandleTypes = dispatcherAnnotation.dataHandleTypes();
        initName();
        initCurTradeDateMap();
        initFundRelevantCodeList();
        initHandlerChains();
        initThreadPoolExecutor();
    }

    private void initCurTradeDateMap() {

    }

    private void initName() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--------结算任务委派者--------")
                .append("\n市场：")
                .append(Arrays.toString(marketCodes))
                .append(",")
                .append("\n业务类型：");
        for (int i = 0; i < businessTypes.length; i++) {
            if (i != businessTypes.length - 1) {
                sb.append(businessTypes[i].getName()).append("、");
            } else {
                sb.append(businessTypes[i].getName());
            }
        }
        sb.append("\n数据操作类型：");
        for (int i = 0; i < dataHandleTypes.length; i++) {
            if (i != dataHandleTypes.length - 1) {
                sb.append(dataHandleTypes[i].getName()).append("、");
            } else {
                sb.append(dataHandleTypes[i].getName());
            }
        }
        sb.append("\n-----------------------------\n");
        name = sb.toString();
    }

    private void initThreadPoolExecutor() {
        int size = handlerChains.size();
        countDownLatch = new CountDownLatch(size);
        threadExecutor = new ThreadPoolExecutor(size, size,
                0L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(size), r -> new Thread(r, name));
    }

    private void initHandlerChains() {
        List<BaseSettlementHandler> firstHandlers = SettlementHandlerBeanFactory.getFirstHandlersOfDispatcher(dispatcherCls);
        for (BaseSettlementHandler handler : firstHandlers) {
            handlerChains.add(new SettlementHandlerChain(handler, curTradeDateMap, fundRelevantCodeList));
        }
    }

    private void initFundRelevantCodeList() {

    }

    public void onlyHandleEffectiveFunds() {
        this.onlyHandleEffectiveFunds = true;
    }

    public void onlyHandleSpecificFunds(List<String> fundCodes) {
        specificFundsList = fundCodes;
    }

    @Override
    public T doDispatch() {
        initStrategies();
        logger.info("{}开始委派任务", name);
        try {
            for (SettlementHandlerChain handlerChain : handlerChains) {
                threadExecutor.execute(() -> {
                    try {
                        Map<Class<? extends ISettlementHandler>, Object> result = handlerChain.execute();
                        resultMap.putAll(result);
                    } catch (Exception e) {
                        throw new SystemException(ExceptionConst.printStackTrace(e));
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new SystemException(ExceptionConst.printStackTrace(e));
        }
        return doHandleResult();
    }

    /**
     * 处理返回结果
     */
    public abstract T doHandleResult();
}
