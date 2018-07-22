package com.github.carlos.service;


import com.github.carlos.common.model.UserVo;
import com.github.carlos.dal.bean.UserDO;

/**
 * Author:chyl2005
 * Date:17/6/14
 * Time:13:43
 * Desc:描述该类的作用
 */
public class ConvertUtils {

    private ConvertUtils() {
    }

    public static UserVo parseToUserResponse(UserDO userDO) {
        UserVo userVo = new UserVo();
        userVo.setUserId(userDO.getId());
        userVo.setUserName(userDO.getUserName());
        userVo.setTrueName(userDO.getTrueName());
        userVo.setGmtCreated(userDO.getGmtCreated());
        userVo.setGmtModified(userDO.getGmtModified());
        userVo.setIsDel(userDO.getIsDel());
        return userVo;
    }


}
