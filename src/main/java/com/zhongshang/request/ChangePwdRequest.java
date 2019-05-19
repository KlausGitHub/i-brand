package com.zhongshang.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangsheng
 * @date 2019-05-19
 */
@Data
public class ChangePwdRequest implements Serializable {
    private static final long serialVersionUID = 7211444527027635785L;

    private Long customerId;

    private String email;

    private String oldPwd;

    private String newPwd;

}
