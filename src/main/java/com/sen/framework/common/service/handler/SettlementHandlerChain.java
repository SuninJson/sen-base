package com.sen.framework.common.service.handler;

import com.sen.framework.common.bean.VFundRelevantCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Evan Huang
 * @date 2019/5/20
 */
public class SettlementHandlerChain {
    private static final Logger logger = LoggerFactory.getLogger(SettlementHandlerChain.class);
    private ISettlementHandler firstHandler;
    private List<ISettlementHandler> handlerChain = new ArrayList<>();
    private Map<Class<? extends ISettlementHandler>, Object> resultList = new ConcurrentHashMap<>();

    public SettlementHandlerChain(ISettlementHandler firstHandler, Map<String, Date> curTradeDateMap, List<VFundRelevantCode> fundRelevantCodeList) {
        this.firstHandler = firstHandler;
        initSettlementConfiguration(curTradeDateMap, fundRelevantCodeList);
        initHandlerChain();
    }


    /**
     * 初始化业务类型配置
     *
     * @param curTradeDateMap
     * @param fundRelevantCodeList
     */
    private void initSettlementConfiguration(Map<String, Date> curTradeDateMap, List<VFundRelevantCode> fundRelevantCodeList) {
        String marketCode = ((BaseSettlementHandler) firstHandler).getMarketCode();
        firstHandler.setCurTradeDate(curTradeDateMap.get(marketCode));
        firstHandler.setFundInfo(fundRelevantCodeList);
    }

    private void initHandlerChain() {
        handlerChain.add(firstHandler);
        ISettlementHandler nextHandler = firstHandler.getNextHandler();
        while (!(nextHandler instanceof FinalSettlementHandler)) {
            handlerChain.add(nextHandler);
            nextHandler = nextHandler.getNextHandler();
        }
        handlerChain.add(nextHandler);
    }

    public Map<Class<? extends ISettlementHandler>, Object> execute() {
        for (ISettlementHandler handler : handlerChain) {
            logger.info("{}开始执行", handler.getName());
            Object result = handler.doHandle();
            logger.info("{}执行结束", handler.getName());
            resultList.put(handler.getClass(), result);
        }
        return resultList;
    }

    public Class<? extends ISettlementHandler> getFirstHandlerCls() {
        return firstHandler.getClass();
    }
}
