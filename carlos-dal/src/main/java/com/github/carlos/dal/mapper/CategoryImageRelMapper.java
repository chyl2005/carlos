package com.github.carlos.dal.mapper;

import com.github.carlos.dal.bean.CategoryImageRelDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 11:56
 * @description: TODO
 */
@Repository
public interface CategoryImageRelMapper {


    Integer insertOrUpdate(CategoryImageRelDO categoryImageRel);

    int batchInsertOrUpdate(@Param("list") List<CategoryImageRelDO> categoryImageRels);


    List<CategoryImageRelDO> selectByParam(@Param("categoryIds") List<Integer> categoryIds);



    Integer del(@Param("categoryId") Integer itemId, @Param("categoryId") Integer imageId);
}
