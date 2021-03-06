package com.zhongshang.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class PatentDTO {
    private static final long serialVersionUID = 1557661677119L;

    private Long id;

    private Long customerId;

    private int pageSize;   //每页条数

    private int pageNum;    //页数

    /**
     * 名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 价格
     *
     * @mbggenerated
     */
    private BigDecimal price;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private BigDecimal servicePrice;

    /**
     * 头图
     *
     * @mbggenerated
     */
    private String bannerUrl;

    /**
     * 申请号
     *
     * @mbggenerated
     */
    private String applyNo;

    /**
     * 申请时间
     *
     * @mbggenerated
     */
    private Date applyTime;

    /**
     * 专利一级分类
     *
     * @mbggenerated
     */
    private String categoryFir;

    /**
     * 专利二级分类
     *
     * @mbggenerated
     */
    private String categorySec;

    /**
     * 交易类型
     *
     * @mbggenerated
     */
    private String transactionType;

    /**
     * 是否展示 Y：展示 N：不展示
     *
     * @mbggenerated
     */
    private String showFlag;

    /**
     * 是否删除 Y：删除 N：未删除
     *
     * @mbggenerated
     */
    private String deleteFlag;

    /**
     * 审核人
     *
     * @mbggenerated
     */
    private Long auditor;

    /**
     * 审核时间
     *
     * @mbggenerated
     */
    private Date auditTime;

    /**
     * 审核描述
     *
     * @mbggenerated
     */
    private String auditDesc;

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

    private String customerName;
    private String customerLoginName;
    private String customerMobile;

    /**
     * 出售中=0，交易中=1，已售出=2
     */
    private Integer status;
}