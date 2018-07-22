package com.github.carlos.common.model;

import lombok.Data;

import java.util.List;

/**
 * Author:chyl2005
 * Date:17/6/10
 * Time:22:46
 * Desc:描述该类的作用
 */
@Data
public class User extends BaseObject{

    private Integer id;
    private String login;
    private String name;
    private List<Role> roles;


}
