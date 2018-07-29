package com.github.carlos.service;

import com.github.carlos.common.constant.CariosCodeEnum;
import com.github.carlos.common.exception.CariosException;
import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.ItemRequest;
import com.github.carlos.common.model.QueryImageParam;
import com.github.carlos.dal.bean.ImageDO;
import com.github.carlos.dal.bean.ItemImageRelDO;
import com.github.carlos.dal.mapper.ImageMapper;
import com.github.carlos.dal.mapper.ItemImageRelMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/29 14:38
 * @description: TODO
 */
@Service
public class ItemImageRelService {

    @Autowired
    private ItemImageRelMapper itemImageRelMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insertOrUpdate(ItemRequest itemRequest) {
        List<ItemImageRelDO> imageRelDOS = ItemConvertUtils.convertToItemImageRelDO(itemRequest, itemRequest.getItemId());
        if (CollectionUtils.isEmpty(imageRelDOS) || itemRequest.getItemId() == null) {
            throw new CariosException(CariosCodeEnum.ILLEGAL_PARAMS, "参数错误！");
        }
        itemImageRelMapper.batchInsertOrUpdate(imageRelDOS);
        return true;

    }


    /**
     * 根据商品ID获取图片列表
     *
     * @return
     */
    public AoData getImageList(List<Integer> itemIds) {
        if (CollectionUtils.isEmpty(itemIds)) {
            return null;
        }
        List<ItemImageRelDO> imageRelDOS = itemImageRelMapper.selectByItemIds(itemIds);
        List<Integer> imageIds = imageRelDOS.stream().map(image -> image.getImageId()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(imageIds)) {
            AoData aoData = new AoData(0, null);
            return aoData;
        }
        QueryImageParam imageParam = new QueryImageParam();
        imageParam.setImageIds(imageIds);
        List<ImageDO> imageDOS = imageMapper.selectByParam(imageParam);
        Integer count = imageMapper.selectByCount(imageParam);
        AoData aoData = new AoData(count, imageDOS);
        return aoData;
    }


    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean del(Integer itemId, Integer imageId) {
        itemImageRelMapper.delByItemIdAndImageId(itemId, imageId);
        return true;

    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean del(Integer itemId) {
        itemImageRelMapper.del(itemId);
        return true;

    }

}
