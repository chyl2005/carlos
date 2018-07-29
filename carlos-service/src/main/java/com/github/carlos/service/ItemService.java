package com.github.carlos.service;

import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.ItemRequest;
import com.github.carlos.common.model.ItemResponse;
import com.github.carlos.common.model.QueryImageParam;
import com.github.carlos.common.model.QueryItemParam;
import com.github.carlos.common.utils.JsonUtils;
import com.github.carlos.dal.bean.ImageDO;
import com.github.carlos.dal.bean.ItemDetailDO;
import com.github.carlos.dal.bean.ItemImageRelDO;
import com.github.carlos.dal.bean.ItemInfoDO;
import com.github.carlos.dal.mapper.CategoryMapper;
import com.github.carlos.dal.mapper.ImageMapper;
import com.github.carlos.dal.mapper.ItemDetailMapper;
import com.github.carlos.dal.mapper.ItemImageRelMapper;
import com.github.carlos.dal.mapper.ItemInfoMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 12:34
 * @description: 商品服务
 */
@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemDetailMapper itemDetailMapper;
    @Autowired
    private ItemInfoMapper itemInfoMapper;
    @Autowired
    private ItemImageRelMapper itemImageRelMapper;

    @Autowired
    private ImageMapper imageMapper;


    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 保存或更新产品信息
     *
     * @param itemRequest
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean saveOrUpdateItem(ItemRequest itemRequest) {
        LOGGER.info("saveItem itemRequest={}", JsonUtils.object2Json(itemRequest));
        //保存产品信息
        ItemInfoDO itemInfoDO = ItemConvertUtils.convertToItemInfoDO(itemRequest);
        itemInfoMapper.insertOrUpdate(itemInfoDO);
        Integer itemId = itemInfoDO.getId();
        //保存产品详情
        ItemDetailDO itemDetailDO = ItemConvertUtils.convertToItemDetailDO(itemRequest, itemId);

        itemDetailMapper.insertOrUpdate(itemDetailDO);
        //更新产品关联的图片
        List<ItemImageRelDO> imageRelDOS = ItemConvertUtils.convertToItemImageRelDO(itemRequest, itemId);
        if (CollectionUtils.isNotEmpty(imageRelDOS)) {
            itemImageRelMapper.batchInsertOrUpdate(imageRelDOS);
        }
        LOGGER.info("saveItem 保存成功 itemRequest={}", JsonUtils.object2Json(itemRequest));
        return true;
    }


    /**
     * 商品列表
     *
     * @return
     */
    public AoData getPageItemList(QueryItemParam param) {
        List<ItemInfoDO> itemInfos = itemInfoMapper.selectByParam(param);
        Integer count = itemInfoMapper.selectByCount(param);
        List<Integer> itemIds = itemInfos.stream().map(itemInfoDO -> itemInfoDO.getId()).collect(Collectors.toList());
        List<ItemDetailDO> itemDetails = itemDetailMapper.selectByItemIds(itemIds);
        Map<Integer, ItemDetailDO> detailMap = itemDetails.stream().collect(Collectors.toMap(item -> item.getItemId(), item -> item));
        List<ItemResponse> itemResponses = itemInfos.stream().map(item -> {
            ItemResponse itemResponse = parseToItemResponse(item, detailMap.get(item.getId()));
            return itemResponse;
        }).collect(Collectors.toList());

        AoData aoData = new AoData(count, itemResponses);
        return aoData;
    }


    private ItemResponse parseToItemResponse(ItemInfoDO itemInfoDO, ItemDetailDO itemDetailDO) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setItemId(itemInfoDO.getId());
        itemResponse.setItemName(itemInfoDO.getItemName());
        itemResponse.setItemNum(itemInfoDO.getItemNum());
        itemResponse.setGmtCreated(itemInfoDO.getGmtCreated());
        itemResponse.setGmtModified(itemInfoDO.getGmtModified());
        itemResponse.setIsDel(itemInfoDO.getIsDel());
        itemResponse.setCategoryId(itemInfoDO.getCategoryId());
        if (itemDetailDO != null) {
            itemResponse.setDescription(itemDetailDO.getDescription());
            itemResponse.setSpecial(itemDetailDO.getSpecial());
        }

        return itemResponse;


    }


    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean del(Integer itemId) {
        itemInfoMapper.del(itemId);
        return true;

    }


}
