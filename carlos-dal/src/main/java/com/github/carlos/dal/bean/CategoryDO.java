package com.github.carlos.dal.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 12:05
 * @description: TODO
 */


@Data
public class CategoryDO {


    private Integer id;


    private String categoryName;

    private Date gmtCreated;

    private Date gmtModified;

    private Integer isDel;
}
