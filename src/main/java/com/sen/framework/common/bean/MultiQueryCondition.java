package com.sen.framework.common.bean;

import lombok.Data;

import java.util.List;

/**
 * @author Evan Huang
 * @date 2019/1/10
 */
@Data
public class MultiQueryCondition {
    private List<SingleQueryCondition> singleQueryConditionList;
}
