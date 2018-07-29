package com.github.carlos.dal.mapper;

import com.github.carlos.dal.bean.CategoryDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 12:07
 * @description: TODO
 */
@Repository
public interface CategoryMapper {



    Integer insertOrUpdate(CategoryDO categoryDO);


    List<CategoryDO> selectAll();

    List<CategoryDO> selectByParam(@Param("type") Integer type);


    Integer del(@Param("id") Integer id);
}
