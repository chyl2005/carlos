package com.github.carlos.dal.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 11:41
 * @description: 产品详情
 */
@Data
public class ItemDetailDO {

    private Integer id;



    private Integer itemId;

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
