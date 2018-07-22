package com.github.carlos.common.model;

import lombok.Data;

import java.util.Set;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/22 17:04
 * @description: TODO
 */
@Data
public class QueryAuthorityParam {


    private Set<Integer> roleIds;

    private Integer menuId;

}
