package com.zhongshang.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangsheng
 * @date 2019-05-12
 */
@Data
public class RegisterRequest implements Serializable {
    private static final long serialVersionUID = 8136436022755244545L;

    private String email;

    private String password;


}
