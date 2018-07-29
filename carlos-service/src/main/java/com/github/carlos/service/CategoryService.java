package com.github.carlos.service;

import com.github.carlos.common.constant.CariosCodeEnum;
import com.github.carlos.common.constant.CateTypeEnum;
import com.github.carlos.common.exception.CariosException;
import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.CategoryResponse;
import com.github.carlos.common.model.ImageMarkRequest;
import com.github.carlos.common.model.QueryImageParam;
import com.github.carlos.dal.bean.CategoryDO;
import com.github.carlos.dal.bean.CategoryImageRelDO;
import com.github.carlos.dal.bean.ImageDO;
import com.github.carlos.dal.bean.ItemImageRelDO;
import com.github.carlos.dal.mapper.CategoryImageRelMapper;
import com.github.carlos.dal.mapper.CategoryMapper;
import com.github.carlos.dal.mapper.ImageMapper;
import com.google.common.base.Joiner;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 13:09
 * @description: TODO
 */
@Service
public class CategoryService {


    @Value(value = "${image.host}")
    private String imageHost;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryImageRelMapper categoryImageRelMapper;

    @Autowired
    private ImageMapper imageMapper;


    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insertOrUpdate(CategoryDO categoryDO) {
        categoryMapper.insertOrUpdate(categoryDO);
        return true;
    }


    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean mark(ImageMarkRequest markRequest) {
        List<Integer> categoryIds = markRequest.getCategoryIds();
        if (CollectionUtils.isEmpty(categoryIds)) {
            throw new CariosException(CariosCodeEnum.ILLEGAL_PARAMS);
        }
        List<CategoryImageRelDO> imageRelDOS = categoryIds.stream().map(cid -> {
            CategoryImageRelDO categoryImageRel = new CategoryImageRelDO();
            categoryImageRel.setCategoryId(cid);
            categoryImageRel.setImageId(markRequest.getImageId());
            return categoryImageRel;
        }).collect(Collectors.toList());
        categoryImageRelMapper.batchInsertOrUpdate(imageRelDOS);
        return true;
    }


    public AoData getCategoryList() {
        List<CategoryDO> categoryDOS = categoryMapper.selectAll();
        AoData aoData = new AoData(categoryDOS.size(), categoryDOS);
        return aoData;
    }


    /**
     * 获取首页轮播图
     *
     * @return
     */
    public List<ImageDO> getBanners() {
        List<CategoryDO> banner = categoryMapper.selectByParam(CateTypeEnum.BANNER.getCode());
        List<Integer> categoryIds = banner.stream().map(categoryDO -> categoryDO.getId()).collect(Collectors.toList());
        List<CategoryImageRelDO> imageRelDOS = categoryImageRelMapper.selectByParam(categoryIds);
        List<Integer> imageIds = imageRelDOS.stream().map(ref -> ref.getImageId()).collect(Collectors.toList());
        QueryImageParam param = new QueryImageParam();
        param.setImageIds(imageIds);
        List<ImageDO> images = imageMapper.selectByParam(param);
        return images;
    }


    /**
     * 分类图片
     *
     * @return
     */
    public List<CategoryResponse> getCategorys() {
        List<CategoryDO> categorys = categoryMapper.selectByParam(CateTypeEnum.ITEM.getCode());
        List<Integer> categoryIds = categorys.stream().map(categoryDO -> categoryDO.getId()).collect(Collectors.toList());
        Map<Integer, ImageDO> result = new HashMap<>();
        if (CollectionUtils.isNotEmpty(categoryIds)) {
            List<CategoryImageRelDO> imageRelDOS = categoryImageRelMapper.selectByParam(categoryIds);
            List<Integer> imageIds = imageRelDOS.stream().map(ref -> ref.getImageId()).collect(Collectors.toList());
            QueryImageParam param = new QueryImageParam();
            param.setImageIds(imageIds);
            List<ImageDO> images = imageMapper.selectByParam(param);
            Map<Integer, ImageDO> imageMap = images.stream().collect(Collectors.toMap(img -> img.getId(), img -> img));
            for (CategoryImageRelDO imageRelDO : imageRelDOS) {
                ImageDO imageDO = imageMap.get(imageRelDO.getImageId());
                if (imageDO != null) {
                    result.put(imageRelDO.getCategoryId(), imageDO);
                }
            }

        }
        List<CategoryResponse> responses = categorys.stream().map(categoryDO -> {
            CategoryResponse response = new CategoryResponse();
            response.setCategoryId(categoryDO.getId());
            response.setCategoryName(categoryDO.getCategoryName());
            ImageDO imageDO = result.get(categoryDO.getId());
            if (imageDO != null) {
                response.setImageUrl(Joiner.on("").join(imageHost, imageDO.getOriginPath()));
            }
            return response;
        }).collect(Collectors.toList());


        return responses;
    }

}
