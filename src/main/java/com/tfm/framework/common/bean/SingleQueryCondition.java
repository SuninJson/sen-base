package com.tfm.framework.common.bean;

import lombok.Data;

import java.util.List;

/**
 * 单个查询规则
 *
 * @author Evan Huang
 * @date 2019/1/10
 */
@Data
public class SingleQueryCondition {
    private String operation;
    private String column;
    private List<Object> valueList;
}
