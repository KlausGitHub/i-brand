package com.zhongshang.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class PubCollectDTO {
    private static final long serialVersionUID = 1560657140887L;

    private Long id;

    /**
     * 客户id
     *
     * @mbggenerated
     */
    private Long customerId;

    /**
     * 操作类型1-发布 2-收藏
     *
     * @mbggenerated
     */
    private Integer actionType;

    /**
     * 类型1-商标2-专利3-服务
     *
     * @mbggenerated
     */
    private Integer contentType;

    /**
     * 收藏的目标id
     *
     * @mbggenerated
     */
    private Long targetId;

    /**
     * 标题
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 预算价格
     *
     * @mbggenerated
     */
    private BigDecimal budget;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private BigDecimal serviceFee;

    /**
     * 分类信息
     *
     * @mbggenerated
     */
    private String jsonStr;

    /**
     * 描述
     *
     * @mbggenerated
     */
    private String description;

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
     * 逻辑删除标记Y已删除N未删除
     */
    private String isDeleted;
}