package com.github.carlos.common.model;

import lombok.Data;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/29 20:10
 * @description: TODO
 */
@Data
public class CategoryResponse {



    private Integer categoryId;


    private String categoryName;
    private String description;

    private String imageUrl;
}
