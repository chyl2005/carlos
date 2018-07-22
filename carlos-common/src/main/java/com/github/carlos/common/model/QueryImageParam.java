package com.github.carlos.common.model;

import lombok.Data;

import java.util.List;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/22 19:02
 * @description: TODO
 */
@Data
public class QueryImageParam extends PageParam{


    private List<Integer> imageIds;



}
