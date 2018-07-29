package com.github.carlos.dal.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 11:41
 * @description: 产品图片关系表
 */
@Data
public class CategoryImageRelDO {

    private Integer id;


    /**
     * 分类ID
     */
    private Integer categoryId;

    private Integer imageId;



    private Date gmtCreated;

    private Date gmtModified;

    private Integer isDel;
}
