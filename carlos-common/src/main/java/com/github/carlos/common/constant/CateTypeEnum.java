package com.github.carlos.common.constant;

/**
 * Author:chyl2005
 * Date:17/4/23
 * Time:11:34
 * Desc:描述该类的作用
 */
public enum CateTypeEnum {



    ITEM(1, "商品分类"),
    BANNER(2, "首页轮播图");

    private Integer code;
    private String name;

    public static final Integer ROOT = 0;

    CateTypeEnum(Integer code, String name) {
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
