package com.tfm.framework.common.service.dispatcher;

/**
 * @author Evan Huang
 * @date 2019/5/20
 */
public interface IDispatcher<T> {

    /**
     * 分派任务
     */
    T doDispatch();
}
