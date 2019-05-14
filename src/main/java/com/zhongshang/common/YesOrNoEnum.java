package com.zhongshang.common;

import org.apache.commons.lang.StringUtils;

public enum YesOrNoEnum {

    YES("Y", "是"),
    NO("N", "否"),
    ;

    private String code;

    private String name;

    YesOrNoEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static YesOrNoEnum getEnumByCode(String code) {
        for (YesOrNoEnum an : YesOrNoEnum.values()) {
            if (code.equals(an.getCode())) {
                return an;
            }
        }
        return null;
    }

    public static YesOrNoEnum getEnumByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (YesOrNoEnum an : YesOrNoEnum.values()) {
            if (an.getName().equals(name)) {
                return an;
            }
        }
        throw new IllegalArgumentException("不支持的状态:" + name);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
