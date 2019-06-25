package com.sen.framework.common.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author Huang Sen
 * @date 2019/6/25
 * @description
 */
public interface BaseControllerDeletable<T> extends BaseControllerOperable<T> {

    @ApiOperation(value = "通用删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "inParam", value = "添加接口入参", dataType = "T")
    })
    @DeleteMapping("/delete")
    default void delete(@RequestBody Map<String, Object> columnMap) {
        IService<T> service = getIService();
        service.removeByMap(columnMap);
    }
}
