package com.github.carlos.dal.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 11:41
 * @description: 产品信息
 */
@Data
public class ItemInfoDO {

    private Integer id;


    /**
     * 分类ID
     */
    private Integer categoryId;

    private String itemName;

    private String itemNum;


    private String coverImg;


    private Date gmtCreated;

    private Date gmtModified;

    private Integer isDel;
}
