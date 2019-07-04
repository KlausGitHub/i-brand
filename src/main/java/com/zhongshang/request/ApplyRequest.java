package com.zhongshang.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yangsheng
 * @date 2019-06-15
 */
@Data
public class ApplyRequest implements Serializable {
    private static final long serialVersionUID = 5668151022195693959L;

    private Long customerId;

    private Integer applyType;

    private Long targetId;//目标id 如果是申请会员可以不用传

    private BigDecimal amount;

    private Integer score;
}
