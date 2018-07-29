package com.github.carlos.service;

import com.github.carlos.common.model.ItemRequest;
import com.github.carlos.dal.bean.ItemDetailDO;
import com.github.carlos.dal.bean.ItemImageRelDO;
import com.github.carlos.dal.bean.ItemInfoDO;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 14:47
 * @description: TODO
 */
public class ItemConvertUtils {


    public static ItemInfoDO convertToItemInfoDO(final ItemRequest itemRequest) {
        ItemInfoDO itemInfoDO = new ItemInfoDO();
        itemInfoDO.setItemName(itemRequest.getItemName());
        itemInfoDO.setItemNum(itemRequest.getItemNum());
        itemInfoDO.setCategoryId(itemRequest.getCategoryId());
        return itemInfoDO;
    }


    public static ItemDetailDO convertToItemDetailDO(final ItemRequest itemRequest, final Integer itemId) {

        ItemDetailDO itemDetailDO = new ItemDetailDO();
        itemDetailDO.setItemId(itemId);
        itemDetailDO.setDescription(itemRequest.getDescription());
        itemDetailDO.setSpecial(itemRequest.getSpecial());
        return itemDetailDO;
    }

    public static List<ItemImageRelDO> convertToItemImageRelDO(final ItemRequest itemRequest, final Integer itemId) {
        List<Integer> imageIds = itemRequest.getImageIds();
        if (CollectionUtils.isEmpty(imageIds)) {
            return new ArrayList<>();
        }
        List<ItemImageRelDO> imageRelDOS = imageIds.stream().map(imageId -> {
            ItemImageRelDO itemDetailDO = new ItemImageRelDO();
            itemDetailDO.setImageId(imageId);
            itemDetailDO.setItemId(itemId);
            return itemDetailDO;
        }).collect(Collectors.toList());
        return imageRelDOS;
    }
}
