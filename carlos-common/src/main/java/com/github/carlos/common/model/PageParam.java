package com.github.carlos.common.model;

import lombok.Data;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/22 18:35
 * @description: TODO
 */
@Data
public class PageParam extends  BaseObject{

    private Integer startRow;
    private Integer pageSize;
}
