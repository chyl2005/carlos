package com.github.carlos.dal.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 11:41
 * @description: 图片信息
 */
@Data
public class ImageDO {


    /**
     * 图片ID
     */
    private Integer id;

    /**
     * 图片名
     */
    private String imageName;

    /**
     * 200路径
     */
    private String img200;
    /**
     * 800路径
     */
    private String img800;

    /**
     * 原图路径
     */
    private String originPath;


    private Date gmtCreated;

    private Date gmtModified;

    private Integer isDel;
}
