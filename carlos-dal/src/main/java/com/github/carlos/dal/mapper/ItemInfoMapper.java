package com.github.carlos.dal.mapper;

import com.github.carlos.common.model.QueryItemParam;
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
public interface ItemInfoMapper {


    Integer insertOrUpdate(ItemInfoDO itemInfoDO);


    List<ItemInfoDO> selectByParam(QueryItemParam param);

    Integer selectByCount(QueryItemParam param);

    Integer del(@Param("id") Integer id);


    ItemInfoDO findById(@Param("id") Integer id);
}
