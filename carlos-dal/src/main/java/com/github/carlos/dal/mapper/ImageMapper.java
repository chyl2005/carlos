package com.github.carlos.dal.mapper;

import com.github.carlos.common.model.QueryImageParam;
import com.github.carlos.dal.bean.ImageDO;
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
public interface ImageMapper {


    Integer insertOrUpdate(ImageDO imageDO);


    List<ImageDO> selectByParam(QueryImageParam param);

    Integer selectByCount(QueryImageParam param);


    Integer del(@Param("id") Integer id);
}
