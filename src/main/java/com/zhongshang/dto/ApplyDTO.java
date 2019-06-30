package com.zhongshang.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApplyDTO {
    private static final long serialVersionUID = 1560566581716L;

    private Long id;
    /**
     * 客户id
     *
     * @mbggenerated
     */
    private Long customerId;

    /**
     * 申请类型1-商标2-专利3-服务4-会员
     *
     * @mbggenerated
     */
    private Integer applyType;

    /**
     * 申请的目标id
     *
     * @mbggenerated
     */
    private Long targetId;

    /**
     * 申请状态0已申请1已成交2拒绝
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 申请金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * 描述
     */
    private String description;

    /**
     * 逻辑删除标记Y已删除N未删除
     */
    private String isDeleted;

    /**
     * 购买方
     */
    private String buyerMobile;

    /**
     * 卖方
     */
    private String sallerMobile;
}