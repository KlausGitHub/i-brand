package com.zhongshang.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class BrandDO {
    private static final long serialVersionUID = 1557661677116L;

    private Long id;

    private Long customerId;

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
     * 商标一级分类
     *
     * @mbggenerated
     */
    private String categoryFir;

    /**
     * 商标二级分类
     *
     * @mbggenerated
     */
    private String categorySec;

    /**
     * 头图
     *
     * @mbggenerated
     */
    private String bannerUrl;

    /**
     * 初审公告期号
     *
     * @mbggenerated
     */
    private String firstNoticeNo;

    /**
     * 初审公告日期
     *
     * @mbggenerated
     */
    private Date firstNoticeTime;

    /**
     * 适用范围
     *
     * @mbggenerated
     */
    private String applyRange;

    /**
     * 注册公告期号
     *
     * @mbggenerated
     */
    private String registerNoticeNo;

    /**
     * 注册公告日期
     *
     * @mbggenerated
     */
    private Date registerNoticeTime;

    /**
     * Y:国内商标 N:国外商标
     *
     * @mbggenerated
     */
    private String inlandFlag;

    /**
     * 商标归属区域 国内商标存省份、国际商标存国家
     *
     * @mbggenerated
     */
    private String areaCode;

    /**
     * 后期指定日期
     *
     * @mbggenerated
     */
    private Date laterAppointTime;

    /**
     * 到期日期
     *
     * @mbggenerated
     */
    private Date expireTime;

    /**
     * 优先权日期
     *
     * @mbggenerated
     */
    private Date priorityTime;

    /**
     * 国际注册日期
     *
     * @mbggenerated
     */
    private Date internationRegisterTime;

    /**
     * 专用期限
     *
     * @mbggenerated
     */
    private String deadlineyear;

    /**
     * 是否共用商标
     *
     * @mbggenerated
     */
    private String publicFlag;

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
     * 明标标识 Y:明标 N:非明标
     *
     * @mbggenerated
     */
    private String vipFlag;

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
     * 出售中=0，交易中=1，已售出=2
     */
    private Integer status;
}