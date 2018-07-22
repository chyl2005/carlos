package com.github.carlos.dal.mapper;

import com.github.carlos.dal.bean.UserRoleDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRoleMapper  {


    /**
     *
     * @param userIds
     * @return
     */
     List<UserRoleDO> findByUserIds(@Param("userIds") Set<Integer> userIds);


    /**
     *
     * @param userId
     * @return
     */
     List<UserRoleDO> getUserRole(@Param("userId")Integer userId);

    /**
     *
     * @param entity
     * @return
     */
     Integer insertOrUpdate(UserRoleDO entity) ;

    /**
     *
     * @param userId
     * @param roleId
     * @return
     */
     Boolean deleteByUserIdAndRoleId(@Param("userId")Integer userId,@Param("roleId") Integer roleId) ;
}
