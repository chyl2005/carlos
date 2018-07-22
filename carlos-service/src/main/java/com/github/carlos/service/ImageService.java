package com.github.carlos.service;

import com.github.carlos.common.model.ImageRequest;
import com.github.carlos.common.model.ImageResponse;
import com.github.carlos.dal.bean.ImageDO;
import com.github.carlos.dal.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 15:42
 * @description: TODO
 */

@Service
public class ImageService {


    @Autowired
    private ImageMapper imageMapper;


    public ImageResponse saveImage(ImageRequest imageRequest) {
        ImageResponse imageResponse = new ImageResponse();
        String imageName="";

        String path="";

        //生成文件名
        //存盘

        ImageDO imageDO = new ImageDO();
        imageDO.setImageName(imageName);
        imageDO.setImg200(path);
        imageDO.setImg800(path);
        imageDO.setOriginPath(path);
        imageMapper.insertOrUpdate(imageDO);
        imageResponse.setImageId(imageDO.getId());
        imageResponse.setImageUrl(imageDO.getOriginPath());
        return imageResponse;
    }
}
