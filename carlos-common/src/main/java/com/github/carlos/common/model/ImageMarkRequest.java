package com.github.carlos.common.model;

import lombok.Data;

import java.util.List;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/29 21:51
 * @description: TODO
 */
@Data
public class ImageMarkRequest extends BaseObject{



    private Integer imageId;

    private List<Integer> categoryIds;

}
