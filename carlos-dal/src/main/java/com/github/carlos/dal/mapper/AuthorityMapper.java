package com.github.carlos.dal.mapper;


import com.github.carlos.common.model.QueryAuthorityParam;
import com.github.carlos.dal.bean.AuthorityDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenyanlong
 * @ClassName: AuthorityDao
 * @Description: 权限
 * @date 2015年11月23日 下午3:05:50
 */
@Repository
public interface AuthorityMapper {

    /**
     * @param roleId
     * @param menuId 模块id
     * @return AuthorityEntity 返回类型
     * @Description: 查询权限
     * @author chenyanlong
     * @date 2015年11月23日 下午3:27:58
     */
    AuthorityDO getAuthority(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);


    /**
     * @return List<AuthorityEntity> 返回类型
     * @Description: 权限列表
     * @author chenyanlong
     * @date 2015年11月23日 下午4:09:27
     */
    List<AuthorityDO> selectByParam(QueryAuthorityParam param);

    Integer selectByCount(QueryAuthorityParam param);



    /**
     * @param authorityDO
     * @return AuthorityEntity 返回类型
     * @Description: 新建或者保存权限
     * @author chenyanlong
     * @date 2015年11月23日 下午3:44:51
     */
    Integer insertOrUpdate(AuthorityDO authorityDO);

    Integer delByRoleId(@Param("roleId") Integer roleId);
}
