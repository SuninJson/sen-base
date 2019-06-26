package com.sen.framework.common.web;

import com.sen.framework.common.exception.ErrorCode;
import lombok.Data;

/**
 * @author Huang Sen
 * @date 2019/6/20
 * @description REST API返回统一格式数据
 */
@Data
public class ApiResult {
    /**
     * 返回状态信息,默认200
     */
    private int code = 200;
    /**
     * 业务异常码
     */
    private String codeDetail = "";
    /**
     * 返回的数据
     */
    private Object data = null;
    /**
     * 返回的信息
     */
    private String msg = "";
    /**
     * 异常堆栈
     */
    private String msgDetail = "";

    private String moreInfo = "";
    /**
     * 标识请求的线程id
     */
    private String requestId = "";

    public ApiResult() {

    }

    public ApiResult(int code, String msg, String msgDetail) {
        this(code, null, msg, msgDetail, null, null);
    }

    public ApiResult(int code, Object data, String msg, String msgDetail) {
        this(code, data, msg, msgDetail, null, null);
    }

    public ApiResult(int code, Object data, String msg, String msgDetail, String codeDetail, String moreInfo) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.msgDetail = msgDetail;
        this.codeDetail = codeDetail;
        this.moreInfo = moreInfo;
        this.requestId = null;//todo
    }

    public static ApiResult valueOf(Object responseBody) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(ErrorCode.OK.getCode());
        apiResult.setData(responseBody);
        return apiResult;
    }

    public static ApiResult errorOf(ErrorCode errorCode, String message) {
        return new ApiResult(errorCode.getCode(), message, null);
    }
}