package com.tfm.framework.common.service.handler;


import com.tfm.framework.common.service.configuration.SettlementConfiguration;

/**
 * @author Evan Huang
 * @date 2019/5/20
 */
public class FinalSettlementHandler extends BaseSettlementHandler {

    public FinalSettlementHandler(SettlementConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Object doHandle() {
        return null;
    }
}
