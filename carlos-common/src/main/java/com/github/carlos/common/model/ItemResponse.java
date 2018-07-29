package com.github.carlos.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/29 11:16
 * @description: TODO
 */
@Data
@NoArgsConstructor
public class ItemResponse extends BaseObject {


    private Integer itemId;

    private String itemName;

    /**
     * 分类ID
     */
    private Integer categoryId;
    /**
     * 编号
     */
    private String itemNum;

    /**
     * 简介
     */
    private String description;

    /**
     * 特征
     */
    private String special;


    private Date gmtCreated;

    private Date gmtModified;

    private Integer isDel;


}
