package com.github.carlos.dal.mapper;


import com.github.carlos.common.model.QueryRoleParam;
import com.github.carlos.dal.bean.RoleDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author chenyanlong
 * @ClassName: RoleDao
 * @Description: 角色
 * @date 2015年11月24日 下午1:21:34
 */
@Repository
public interface RoleMapper  {




    List<RoleDO> selecAll() ;


  Integer selecByCount(QueryRoleParam param) ;

    /**
     *
     * @param param
     * @return
     */
    List<RoleDO> selecByParam(QueryRoleParam param) ;

    /**
     * @param roleDO
     * @return RoleEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月24日 下午1:19:24
     */
     Integer insertOrUpdate(RoleDO roleDO) ;

    /**
     * @param roleId
     * @param isDel
     * @return Boolean 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月24日 下午1:20:07
     */
     Integer del(@Param("roleId") Integer roleId,@Param("isDel") Integer isDel) ;



    /**
     * @return RoleEntity    返回类型
     * @Description:
     * @author chenyanlong
     * @date 2016年3月17日 上午9:14:42
     */
     RoleDO findById(@Param("id")Integer id);
}
