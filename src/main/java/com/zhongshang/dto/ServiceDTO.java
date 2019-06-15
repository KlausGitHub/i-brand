package com.zhongshang.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceDTO {
    private static final long serialVersionUID = 1560587583321L;

    private Long id;

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
     * 成交数
     *
     * @mbggenerated
     */
    private Long total;

    /**
     * 头图
     *
     * @mbggenerated
     */
    private String bannerUrl;

    /**
     * 内容
     *
     * @mbggenerated
     */
    private String content;

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
}