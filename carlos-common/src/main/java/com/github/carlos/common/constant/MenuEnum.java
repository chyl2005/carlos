package com.github.carlos.common.constant;

/**
 * Author:chyl2005
 * Date:17/4/23
 * Time:11:34
 * Desc:描述该类的作用
 */
public enum MenuEnum {

    ROOT_LEVEL(0, "根目录"),
    ONE_LEVEL(1, "一级"),
    TWO_LEVEL(2, "二级"),

    LINK(1, "链接"),
    NOT_LINK(0, "非连接");

    private Integer code;
    private String name;

    public static final Integer ROOT = 0;

    MenuEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
