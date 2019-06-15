package com.zhongshang.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConfigDO {
    private static final long serialVersionUID = 1560587048798L;

    private Long id;
    /**
     * key
     *
     * @mbggenerated
     */
    private String key;

    /**
     * value
     *
     * @mbggenerated
     */
    private String value;

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
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 逻辑删除标记Y已删除N未删除
     */
    private String isDeleted;
}