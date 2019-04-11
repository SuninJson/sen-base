package com.tfm.framework.common.constant;

/**
 * @author Evan Huang
 * @date 2019/5/20
 */
public enum DataHandleType {

    ALL("所有操作"),
    MIGRATION("新产品上线数据迁移"),
    EXECUTION_RECON("成交核对");

    DataHandleType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

}
