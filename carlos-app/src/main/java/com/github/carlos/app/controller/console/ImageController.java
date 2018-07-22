package com.github.carlos.app.controller.console;

import com.github.carlos.common.model.ImageRequest;
import com.github.carlos.common.model.ImageResponse;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.dal.mapper.ImageMapper;
import com.github.carlos.service.ImageService;
import com.github.carlos.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 13:19
 * @description: 产品保存
 */
@Controller
@RequestMapping("/console/image")
public class ImageController {

    @Autowired
    private ImageService imageService;


    /**
     * 更新商品信息
     *
     * @param imageRequest
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public WebResponse upload(ImageRequest imageRequest) {
        WebResponse webResponse = new WebResponse<>();
        ImageResponse imageResponse = imageService.saveImage(imageRequest);
        webResponse.setData(imageResponse);
        return webResponse;
    }

}
