package com.tfm.framework.common.service.configuration;


import com.tfm.framework.common.bean.VFundRelevantCode;
import com.tfm.framework.common.constant.BusinessType;
import com.tfm.framework.common.constant.DataHandleType;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Evan Huang
 * @date 2019/5/16
 */
public class SettlementConfiguration {
    @NotNull(message = "请设置市场代码")
    private String marketCode;
    @NotNull(message = "请设置业务类型")
    private BusinessType businessType;
    @NotNull(message = "请设置数据处理类型")
    private DataHandleType dataHandleType;
    @NotNull(message = "请检查相应市场的交易日")
    private Date curTradeDate;
    @NotNull(message = "程序未成功设置相应产品代码信息")
    private List<VFundRelevantCode> fundRelevantCodeList;

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public DataHandleType getDataHandleType() {
        return dataHandleType;
    }

    public void setDataHandleType(DataHandleType dataHandleType) {
        this.dataHandleType = dataHandleType;
    }

    public Date getCurTradeDate() {
        return curTradeDate;
    }

    public void setCurTradeDate(Date curTradeDate) {
        this.curTradeDate = curTradeDate;
    }

    public List<VFundRelevantCode> getFundRelevantCodeList() {
        return fundRelevantCodeList;
    }

    public void setFundRelevantCodeList(List<VFundRelevantCode> fundRelevantCodeList) {
        this.fundRelevantCodeList = fundRelevantCodeList;
    }
}
