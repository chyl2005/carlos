package com.github.carlos.service;


import com.github.carlos.common.constant.DeleteStatusEnum;
import com.github.carlos.common.model.UserInfoCacheVo;
import com.github.carlos.common.utils.JsonUtils;
import com.github.carlos.dal.bean.LoginInfoDO;
import com.github.carlos.dal.mapper.LoginInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Author:chyl2005
 * Date:17/6/10
 * Time:22:06
 * Desc:描述该类的作用
 */
@Service
public class LoginInfoService  {
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private AuthorityService authorityService;

    /**
     * @param loginKey
     * @param userId
     * @Description:
     * @author chenyanlong
     * @date 2015年11月26日 上午9:20:08
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(String loginKey, Integer userId) {
        UserInfoCacheVo userInfoCache = authorityService.getUserInfoCache(userId);
        LoginInfoDO loginInfo = new LoginInfoDO();
        loginInfo.setLoginKey(loginKey);
        loginInfo.setLoginInfo(JsonUtils.object2Json(userInfoCache));
        loginInfoMapper.insertOrUpdate(loginInfo);

    }

    /**
     * @param loginKey
     * @Description:
     * @author chenyanlong
     * @date 2015年11月26日 上午9:20:12
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void del(String loginKey) {
        loginInfoMapper.del(loginKey);
    }

    /**
     * @param loginKey@Description:
     * @author chenyanlong
     * @date 2015年12月11日 下午2:50:25
     */
    public UserInfoCacheVo getLoginInfo(String loginKey) {
        LoginInfoDO loginInfo = this.loginInfoMapper.getLoginInfo(loginKey);
        if (loginInfo == null) {
            return null;
        }
        return JsonUtils.json2Object(loginInfo.getLoginInfo(), UserInfoCacheVo.class);
    }

}
