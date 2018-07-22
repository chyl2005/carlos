package com.github.carlos.dal.mapper;


import com.github.carlos.dal.bean.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {


    /**
     * @return
     */
    List<UserDO> selectByParam(@Param("isDel") Integer isDel);


    UserDO selectByUserAndPassword(@Param("userName") String userName, @Param("password") String password);


    Integer selectByCount(@Param("isDel") Integer isDel);

    /**
     * @param userDO
     * @return UserEntity    返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月26日 上午9:20:08
     */
    Integer insertOrUpdate(UserDO userDO);


    /**
     * @param @param userId
     * @return UserEntity    返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年12月11日 下午2:50:25
     */
    UserDO findById(@Param("id") Integer id);

    /**
     * @param id
     * @param isDel
     * @return UserEntity    返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年12月21日 下午6:17:31
     */
    Integer updateState(@Param("id") Integer id, @Param("isDel") Integer isDel);

}
