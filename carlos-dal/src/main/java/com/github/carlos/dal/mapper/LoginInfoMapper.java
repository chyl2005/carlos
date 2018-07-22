package com.github.carlos.dal.mapper;

import com.github.carlos.dal.bean.LoginInfoDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Author:chyl2005
 * Date:17/6/10
 * Time:21:22
 * Desc:描述该类的作用
 */
@Repository
public interface LoginInfoMapper {
    /**
     * @return LoginInfoEntity    返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月26日 上午9:20:08
     */
    Integer insertOrUpdate(LoginInfoDO loginInfoDO);



    /**
     * @param loginKey
     * @return LoginInfoEntity    返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月26日 上午9:20:12
     */
    void del(@Param("loginKey") String loginKey);

    /**
     * @Description:
     * @author chenyanlong
     * @date 2015年12月11日 下午2:50:25
     */
    LoginInfoDO getLoginInfo(@Param("loginKey") String loginKey);
}
