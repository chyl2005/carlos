package com.github.carlos.dal.bean;


import com.github.carlos.common.model.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户表
 */
@Data
public class UserDO extends BaseObject {


    private Integer id;
    /**
     * 账号名
     */
    private String userName;
    private String password;
    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 删除标记
     */
    private Integer isDel;


}
