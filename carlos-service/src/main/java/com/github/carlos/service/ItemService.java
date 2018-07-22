package com.github.carlos.service;

import com.github.carlos.common.model.ItemRequest;
import com.github.carlos.common.utils.JsonUtils;
import com.github.carlos.dal.bean.ItemDetailDO;
import com.github.carlos.dal.bean.ItemImageRelDO;
import com.github.carlos.dal.bean.ItemInfoDO;
import com.github.carlos.dal.mapper.CategoryMapper;
import com.github.carlos.dal.mapper.ImageMapper;
import com.github.carlos.dal.mapper.ItemDetailMapper;
import com.github.carlos.dal.mapper.ItemImageRelMapper;
import com.github.carlos.dal.mapper.ItemInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
    @Transactional
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
        itemImageRelMapper.batchInsertOrUpdate(imageRelDOS);
        LOGGER.info("saveItem 保存成功 itemRequest={}", JsonUtils.object2Json(itemRequest));
        return true;
    }


}
