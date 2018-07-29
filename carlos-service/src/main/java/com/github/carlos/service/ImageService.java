package com.github.carlos.service;

import com.github.carlos.common.constant.CariosCodeEnum;
import com.github.carlos.common.exception.CariosException;
import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.ImageRequest;
import com.github.carlos.common.model.ImageResponse;
import com.github.carlos.common.model.QueryImageParam;
import com.github.carlos.common.utils.DateUtils;
import com.github.carlos.common.utils.FileUtils;
import com.github.carlos.common.utils.UUIDUtils;
import com.github.carlos.dal.bean.ImageDO;
import com.github.carlos.dal.bean.ItemImageRelDO;
import com.github.carlos.dal.mapper.ImageMapper;
import com.google.common.base.Joiner;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 15:42
 * @description: TODO
 */

@Service
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @Value(value = "${image.path}")
    private String imagePath;

    @Value(value = "${image.namespace}")
    private String imageNamespace;
    @Autowired
    private ImageMapper imageMapper;


    public AoData getImageList(QueryImageParam param) {
        List<ImageDO> imageDOS = imageMapper.selectByParam(param);
        Integer count = imageMapper.selectByCount(param);
        AoData aoData = new AoData(count, imageDOS);
        return aoData;
    }

    /**
     * 保存图片
     *
     * @param imageRequest
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ImageResponse saveImage(ImageRequest imageRequest) {
        MultipartFile file = imageRequest.getUploadFile();
        String originalFilename = file.getOriginalFilename();
        // 声明新的文件名称和目录路径
        String newFileName = Joiner.on(".").join(UUIDUtils.createUUID(), FileUtils.getSufExtName(originalFilename));
        String relativePath = Joiner.on(File.separator).join(imageNamespace, DateUtils.getDateformat(new Date()), newFileName);
        String filePath = imagePath + relativePath;
        File newFile = FileUtils.createNewFile(filePath);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            LOGGER.error("saveImage   文件解析错误", e);
            throw new CariosException(CariosCodeEnum.DATA_ERROR, "文件解析错误！", e);
        }


        ImageResponse imageResponse = new ImageResponse();
        ImageDO imageDO = new ImageDO();
        imageDO.setImageName(originalFilename);
        imageDO.setImg200(relativePath);
        imageDO.setImg800(relativePath);
        imageDO.setOriginPath(relativePath);
        imageMapper.insertOrUpdate(imageDO);
        imageResponse.setImageId(imageDO.getId());
        imageResponse.setImageUrl(imageDO.getOriginPath());
        return imageResponse;
    }

}
