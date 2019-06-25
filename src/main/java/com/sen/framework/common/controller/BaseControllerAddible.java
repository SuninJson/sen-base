package com.sen.framework.common.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Huang Sen
 * @date 2019/6/25
 * @description
 */
public interface BaseControllerAddible<T> extends BaseControllerOperable<T> {

    @ApiOperation(value = "通用添加接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "inParam", value = "添加接口入参", dataType = "T")
    })
    @PostMapping("/add")
    default void add(@RequestBody T inParam) {
        IService<T> service = getIService();
        service.save(inParam);
    }
}
