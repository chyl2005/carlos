package com.github.carlos.common.model;

import lombok.Data;

import java.util.List;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 14:18
 * @description: TODO
 */
@Data
public class ItemRequest extends BaseObject {


    private Integer itemId;

    private String itemName;


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


    /**
     * 图片ID
     */
    private List<Integer> imageIds;

}
