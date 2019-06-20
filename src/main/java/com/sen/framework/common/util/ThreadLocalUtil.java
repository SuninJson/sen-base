package com.sen.framework.common.util;


import com.sen.framework.common.bean.JwtData;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtil {
    private static final ThreadLocal<Map<String, Object>> DATA_MAP_THREAD_LOCAL = new ThreadLocal<Map<String, Object>>();

    /**
     * 请求url
     */
    public static final String REQUEST_URI = "requestUrl";
    private static final String REQUEST_METHOD = "requestMethod";
    private static final String REQUEST_ID = "requestId";
    private static final String JWT_DATA = "jwtData";
    private static final String IP = "ip";
    private static final String MAC = "mac";
    private static final String REQ_EXTEND_INFO = "request_extend_info";

    /**
     * 额外定义属性
     */
    public static final String EXTEND_FIELDS = "extendFields";
    /**
     * 额外定义查询条件，当前线程使用完需要清除
     */
    public static final String CUSTOM_QUERY_CONDITION = "customQueryCondition";
    public static final String DATA_PRIVILEGE_SHOW = "dataPrivilegeShow";

    /**
     * 设置用户信息
     *
     * @param jwtData
     */
    public static void setJwtData(JwtData jwtData) {
        set("jwtData", jwtData);
    }

    /**
     * 返回用户信息
     *
     * @return
     */
    public static JwtData getJwtData() {
        return get(JWT_DATA);
    }

    /**
     * 获取本次请求ip地址
     *
     * @return
     */
    public static String getIp() {
        return get(IP);
    }

    /**
     * 设置本次请求ip地址
     *
     * @param ip
     */
    public static void setIp(String ip) {
        set(IP, ip);
    }

    /**
     * 获取本次请求mac地址
     *
     * @return
     */
    public static String getMac() {
        return get(MAC);
    }

    /**
     * 设置本次请求mac地址
     *
     * @param mac
     */
    public static void setMac(String mac) {
        set(MAC, mac);
    }

    /**
     * 获取本次请求额外信息
     *
     * @return
     */
    public static Map<String, Object> getReqExtendInfo() {
        return get(REQ_EXTEND_INFO);
    }

    /**
     * 设置本次请求额外信息
     *
     * @param reqExtendInfo
     */
    public static void setReqExtendInfo(Map<String, Object> reqExtendInfo) {
        set(REQ_EXTEND_INFO, reqExtendInfo);
    }

    public static void setRequestMethod(String requestMethod) {
        set(REQUEST_METHOD, requestMethod);
    }

    public static String getRequestMethod() {
        return get(REQUEST_METHOD);
    }

    public static void setRequestId(String requestId) {
        set(REQUEST_ID, requestId);
    }

    public static String getRequestId() {
        return get(REQUEST_ID);
    }

    /**
     * 从当前线程变量副本中返回当前保存的map对象,如果不存在，则进行初始化
     *
     * @return
     */
    private static Map<String, Object> getDataMap() {
        Map<String, Object> dataMap = DATA_MAP_THREAD_LOCAL.get();
        if (dataMap == null) {
            dataMap = new HashMap<>(16);
            DATA_MAP_THREAD_LOCAL.set(dataMap);
        }
        return dataMap;
    }

    /**
     * 清除数据
     */
    public static void clear() {
        getDataMap().clear();
    }

    /**
     * 设置值
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        getDataMap().put(key, value);
    }

    /**
     * 获取值
     *
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) getDataMap().get(key);
    }

    public static void removeThreadLocal() {
        DATA_MAP_THREAD_LOCAL.remove();
    }
}
