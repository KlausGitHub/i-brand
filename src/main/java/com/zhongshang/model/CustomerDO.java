package com.zhongshang.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerDO {
    private static final long serialVersionUID = 1557661677072L;

    private Long id;

    /**
     * 名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 登录名
     *
     * @mbggenerated
     */
    private String loginName;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 邮箱
     *
     * @mbggenerated
     */
    private String email;

    /**
     * 密码
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 会员标识 Y:会员 N:非会员
     *
     * @mbggenerated
     */
    private String vipFlag;

    /**
     * 账户余额
     *
     * @mbggenerated
     */
    private BigDecimal balance;

    /**
     * 头像地址
     *
     * @mbggenerated
     */
    private String headLogo;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 禁用标识 Y：禁用 N：不禁用
     *
     * @mbggenerated
     */
    private String disableFlag;

    /**
     * 最后登录时间
     *
     * @mbggenerated
     */
    private Date lastLoginTime;
}