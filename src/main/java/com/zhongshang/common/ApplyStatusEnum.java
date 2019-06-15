package com.zhongshang.common;

import org.apache.commons.lang.StringUtils;

public enum ApplyStatusEnum {

    APPLY(0, "已申请"),
    SUCCESS(1, "已成交"),
    ;

    private Integer code;

    private String name;

    ApplyStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ApplyStatusEnum getEnumByCode(Integer code) {
        for (ApplyStatusEnum an : ApplyStatusEnum.values()) {
            if (code.equals(an.getCode())) {
                return an;
            }
        }
        return null;
    }

    public static ApplyStatusEnum getEnumByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (ApplyStatusEnum an : ApplyStatusEnum.values()) {
            if (an.getName().equals(name)) {
                return an;
            }
        }
        throw new IllegalArgumentException("不支持的状态:" + name);
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
