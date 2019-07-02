package com.sen.framework.common.controller;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Evan Huang
 * @date 2019/1/9
 */
@Slf4j
public abstract class BaseController<T> implements BaseControllerAddable<T>, BaseControllerDeletable<T>, BaseControllerQueryable<T> {

}
