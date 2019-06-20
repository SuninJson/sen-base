package com.sen.framework.common.constant;

/**
 * @author Evan Huang
 * @date 2019/5/16
 */
public enum BusinessType {
    ALL("所有业务"),
    HONOUR_INTEREST("兑付兑息");

    private String name;

    BusinessType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
