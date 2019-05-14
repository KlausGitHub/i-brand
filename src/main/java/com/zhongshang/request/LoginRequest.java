package com.zhongshang.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangsheng
 * @date 2019-05-12
 */
@Data
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 3578668674698728050L;

    @NotNull
    private String loginName;

    @NotNull
    private String password;

    private Integer loginType;
}
