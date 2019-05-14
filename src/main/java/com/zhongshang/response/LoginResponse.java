package com.zhongshang.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangsheng
 * @date 2019-05-14
 */
@Data
public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 1953826995026512711L;

    private Long customerId;

    private String mobile;
}
