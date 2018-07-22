package com.github.carlos.dal.mapper;

import com.github.carlos.dal.bean.ItemImageRelDO;
import com.github.carlos.dal.bean.ItemInfoDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 11:56
 * @description: TODO
 */
@Repository
public interface ItemImageRelMapper {


    Integer insertOrUpdate(ItemImageRelDO itemImageRelDO);

    int batchInsertOrUpdate(@Param("list") List<ItemImageRelDO> itemImageRelDOS);


    List<ItemImageRelDO> selectByItemIds(@Param("itemIds")List<Integer> itemIds);



    Integer del(@Param("id") Integer id);
}
