package com.github.carlos.common.model;

import lombok.Data;

import java.util.Set;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/22 16:42
 * @description: TODO
 */
@Data
public class QueryMenuParam {


    private Set<Integer> menuIds;


    private Integer parentId;
    private String path;


    private Integer level;


}
