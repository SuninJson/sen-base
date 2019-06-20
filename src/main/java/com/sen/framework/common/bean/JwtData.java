package com.sen.framework.common.bean;

import lombok.Data;

@Data
public class JwtData {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 用户编码
     */
    private String code;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 用户名
     */
    private String name;

    /**
     * 公司ID
     */
    private Long companyId;
    /**
     * 角色ID
     */
    private String roleIds;

    /**
     * token
     */
    private String token;
    /**
     * 登录Ip
     */
    private String ip;
    /**
     * 登录mac地址
     */
    private String mac;
    /**
     * 登录时间
     */
    private Long loginTime;
    /**
     * token 过期时间（s）
     */
    private Long exp;
}
