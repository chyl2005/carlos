package com.github.carlos.common.model;

import com.github.carlos.common.constant.DeleteStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/22 18:34
 * @description: TODO
 */
@Data
public class QueryRoleParam extends PageParam {


    private List<Integer> roleIds;


    /**
     * 删除标记
     */
    private Integer isDel = DeleteStatusEnum.NOT_DEL.getCode();

}
