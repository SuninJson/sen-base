package com.sen.framework.common.controller;

import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseControllerOperable<T> {

    IService<T> getIService();
}
