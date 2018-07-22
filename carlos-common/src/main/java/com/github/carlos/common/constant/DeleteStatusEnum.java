package com.github.carlos.common.constant;

/**
 * Author:chyl2005
 * Date:17/4/23
 * Time:11:34
 * Desc:描述该类的作用
 */
public enum DeleteStatusEnum {

    NOT_DEL(0, "未删除"),
    DEL(1, "已删除");
    private Integer code;
    private String name;

    DeleteStatusEnum(Integer code, String name) {
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
