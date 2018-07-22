package com.github.carlos.common.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author:chyl2005
 * Date:17/6/14
 * Time:12:19
 * Desc:描述该类的作用
 */
@Data
public class UserVo extends BaseObject{



    private Integer userId;
    /**
     * 账号名
     */
    private String userName;
    private String password;
    /**
     *真实姓名
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

    /**
     * 角色名
     */
    private List<String> roleName;


}
