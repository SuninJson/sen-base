package com.sen.framework.common.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sen.framework.common.bean.MultiQueryCondition;
import com.sen.framework.common.bean.SingleQueryCondition;
import com.sen.framework.common.exception.SystemException;
import com.sen.framework.common.util.StringUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Huang Sen
 * @date 2019/6/25
 * @description
 */
public interface BaseControllerQueryable<T> extends BaseControllerOperable<T> {

    @ApiOperation(value = "通用分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页码", dataType = "long", required = true),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页面条目数", dataType = "long", required = true),
            @ApiImplicitParam(paramType = "body", name = "multiQueryCondition", value = "多个查询条件（可为NULL）", dataType = "MultiQueryCondition")
    })
    @PostMapping("/paging-query")
    default IPage<T> pagingQuery(@RequestParam("currentPage") long currentPage, @RequestParam("size") long size,
                                 @RequestBody(required = false) MultiQueryCondition multiQueryCondition) {
        IService<T> service = service();
        //设置分页条件
        Page<T> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(size);
        //设置查询条件
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (null != multiQueryCondition && !CollectionUtils.isEmpty(multiQueryCondition.getQueryConditionList())) {
            setQueryWrapper(queryWrapper, multiQueryCondition);
        }

        return service.page(page, queryWrapper);
    }

    @ApiOperation(value = "通用列表查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "multiQueryCondition", value = "多个查询条件（可为NULL）", dataType = "MultiQueryCondition")
    })
    @PostMapping("/list")
    default List<T> list(@RequestBody(required = false) MultiQueryCondition multiQueryCondition) {
        IService<T> service = service();
        //设置查询条件
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (null != multiQueryCondition && !CollectionUtils.isEmpty(multiQueryCondition.getQueryConditionList())) {
            setQueryWrapper(queryWrapper, multiQueryCondition);
        }
        return service.list(queryWrapper);
    }

    /**
     * 设置查询条件
     *
     * @param queryWrapper        需要设置的查询包装类
     * @param multiQueryCondition 查询条件列表包装类
     * @return 已设置的查询包装类
     */
    default QueryWrapper<T> setQueryWrapper(QueryWrapper<T> queryWrapper, MultiQueryCondition multiQueryCondition) {
        List<SingleQueryCondition> queryConditionList = multiQueryCondition.getQueryConditionList();
        for (SingleQueryCondition queryCondition : queryConditionList) {
            queryWrapper = setQueryWrapper(queryWrapper, queryCondition);
        }
        return queryWrapper;
    }

    default QueryWrapper<T> setQueryWrapper(QueryWrapper<T> queryWrapper, SingleQueryCondition condition) {
        String column = condition.getColumn();
        String operation = condition.getOperation();

        List<Object> queryValueList = new ArrayList<>();
        if (column != null) {
            column = StringUtils.humpToUnderline(column);
            queryValueList.add(column);
        }
        if (condition.getValueList() != null) {
            queryValueList.addAll(condition.getValueList());
        }

        Class<?>[] paramTypes = new Class[queryValueList.size()];
        for (int i = 0; i < queryValueList.size(); i++) {
            paramTypes[i] = Object.class;
        }

        Method method = getQueryWrapperMethod(operation, paramTypes);
        try {
            queryWrapper = (QueryWrapper<T>) method.invoke(queryWrapper, queryValueList.toArray());
        } catch (Exception e) {
            try {
                queryWrapper = (QueryWrapper<T>) method.invoke(queryWrapper, column, condition.getValueList().toArray());
            } catch (Exception e1) {
                throw new SystemException(String.format("%s.%s()方法执行失败",
                        queryWrapper.getClass().getName(), operation));
            }
        }

        return queryWrapper;
    }

    default Method getQueryWrapperMethod(String operation, Class<?>... parameterTypes) {
        Method method;
        try {
            if (ArrayUtils.isNotEmpty(parameterTypes)) {
                method = QueryWrapper.class.getMethod(operation, parameterTypes);
            } else {
                method = QueryWrapper.class.getMethod(operation);
            }
        } catch (NoSuchMethodException e) {
            try {
                method = QueryWrapper.class.getMethod(operation, Object.class, Object[].class);
            } catch (NoSuchMethodException e1) {
                throw new SystemException(String.format("%s未找到%s方法",
                        QueryWrapper.class.getName(), operation));
            }
        }
        return method;
    }

    default QueryWrapper<T> setQueryWrapper(QueryWrapper<T> queryWrapper, Method[] queryWrapperMethods, SingleQueryCondition queryCondition) {
        String operation = queryCondition.getOperation();

        String columnTemp = queryCondition.getColumn();
        String column = (null == columnTemp ? "" : StringUtils.humpToUnderline(columnTemp));

        List<Object> queryValueList = queryCondition.getValueList();
        int valueSize = null != queryValueList ? queryValueList.size() : 0;

        if (Objects.equals("or", operation)) {
            queryWrapper = queryWrapper.or();
        } else if (valueSize < 2) {
            //QueryWrapper的isNull等方法有1个参数,QueryWrapper的eq、like和gt等方法有2个参数
            Method method = getOpMethod(queryWrapperMethods, operation, valueSize + 1);
            try {
                queryWrapper = (QueryWrapper<T>) method.invoke(queryWrapper, column, queryValueList.get(0));
            } catch (Exception e) {
                handleSetConditionException(operation, column, queryValueList);
            }
        } else if (valueSize == 2) {
            //QueryWrapper的between等方法有个3个参数
            Method method = getOpMethod(queryWrapperMethods, operation, 3);
            if (null != method) {
                try {
                    queryWrapper = (QueryWrapper<T>) method.invoke(queryWrapper, column, queryValueList.get(0), queryValueList.get(1));
                } catch (Exception e) {
                    handleSetConditionException(operation, column, queryValueList);
                }
            }
        } else {
            //QueryWrapper的in等方法有2个参数且第二个参数为集合
            Method method = getOpMethod(queryWrapperMethods, operation, 2);
            try {
                queryWrapper = (QueryWrapper<T>) method.invoke(queryWrapper, column, queryValueList);
            } catch (Exception e) {
                handleSetConditionException(operation, column, queryValueList);
            }
        }
        return queryWrapper;
    }

    default void handleSetConditionException(String operation, String column, List<Object> queryValueList) {
        throw new SystemException(
                String.format("分页查询添加查询条件失败,字段为<%s>,操作为<%s>,值为<%s>",
                        column, operation, JSON.toJSONString(queryValueList))
        );
    }

    default Method getOpMethod(Method[] queryWrapperMethods, String operation, int parametersSize) {
        return Arrays.stream(queryWrapperMethods)
                .filter(m -> Objects.equals(m.getName(), operation) && m.getParameters().length == parametersSize)
                .findFirst()
                .orElseGet(null);
    }

    /**
     * 异常处理
     *
     * @param e
     */
    default void handleException(Exception e) {
        // SystemException为系统手动抛出的异常，不处理
        if (e instanceof SystemException) {
            throw (SystemException) e;
        }

        throw new SystemException("数据查询失败: " + e.getMessage(), e);
    }

    static void main(String[] args) {
        Method[] methods = QueryWrapper.class.getMethods();
        Method[] dmethods = QueryWrapper.class.getDeclaredMethods();
        System.out.println();
        List<Method> methodList = Arrays.stream(methods).filter(m -> m.getName().equals("in")).collect(Collectors.toList());
        for (Method method : methodList) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> parameterType : parameterTypes) {
                System.out.print(parameterType.getSimpleName() + ",");
            }
            System.out.println();
        }

    }
}
