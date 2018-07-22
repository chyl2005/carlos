package com.github.carlos.dal.mapper;


import com.github.carlos.common.model.QueryMenuParam;
import com.github.carlos.dal.bean.MenuDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MenuMapper {



    /**
     * @param menu
     * @return MenuEntity 返回类型
     * @Description: 修改
     * @author chenyanlong
     * @date 2015年11月27日 下午3:01:31
     */
    Integer insertOrUpdate(MenuDO menu);


    /**
     * @return List<MenuEntity> 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月23日 下午2:33:08
     */
     List<MenuDO> selectByParam(QueryMenuParam param);

    List<MenuDO> selectAll();

    /**
     *
     * @param param
     * @return
     */
    Integer  selectByCount(QueryMenuParam param);
    /**
     * @param isDel
     * @return Boolean 返回类型
     * @Description: 修改状态
     * @author chenyanlong
     * @date 2015年11月27日 下午3:02:10
     */

     void updateStatus(@Param("id") Integer id, @Param("isDel")Integer isDel);


    /**
     * @param id
     * @return MenuEntity 返回类型
     * @Description:
     * @author chenyanlong
     * @date 2015年11月27日 下午4:55:39
     */
     MenuDO findById(@Param("id")Integer id);



}
