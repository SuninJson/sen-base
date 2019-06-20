package com.sen.framework.common.service.handler;


import com.sen.framework.common.bean.VFundRelevantCode;

import java.util.Date;
import java.util.List;

/**
 * @author Evan Huang
 * @date 2019/5/16
 */
public interface ISettlementHandler {

    Object doHandle();

    ISettlementHandler getNextHandler();

    void setFundInfo(List<VFundRelevantCode> fundRelevantCodeList);

    void setCurTradeDate(Date curTradeDate);

    String getName();
}
