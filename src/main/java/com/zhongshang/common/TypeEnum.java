package com.zhongshang.common;

import org.apache.commons.lang.StringUtils;

public enum TypeEnum {

    BRAND(1, "商标"),
    PATENT(2, "专利"),
    ;

    private Integer code;

    private String name;

    TypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static TypeEnum getEnumByCode(Integer code) {
        for (TypeEnum an : TypeEnum.values()) {
            if (code.equals(an.getCode())) {
                return an;
            }
        }
        return null;
    }

    public static TypeEnum getEnumByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (TypeEnum an : TypeEnum.values()) {
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
