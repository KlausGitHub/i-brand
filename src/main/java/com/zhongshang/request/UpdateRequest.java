package com.zhongshang.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yangsheng
 * @date 2019-05-14
 */
@Data
public class UpdateRequest implements Serializable {
    private static final long serialVersionUID = -6142166726409230079L;

    @NotNull
    private Long customerId;

    private String mobile;

    private String loginName;

    private String headLogo;

    private String name;
}
