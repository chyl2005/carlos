package com.github.carlos.app.controller.console;

import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.ImageMarkRequest;
import com.github.carlos.common.model.ImageRequest;
import com.github.carlos.common.model.ImageResponse;
import com.github.carlos.common.model.QueryImageParam;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.common.utils.PageUtil;
import com.github.carlos.dal.mapper.ImageMapper;
import com.github.carlos.service.CategoryService;
import com.github.carlos.service.ImageService;
import com.github.carlos.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

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
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/index")
    public String index() {
        return "console/image/index";
    }

    @RequestMapping("/uploadimage")
    public String uploadImage() {
        return "console/image/upload";
    }


    @RequestMapping("/imageList")
    @ResponseBody
    public WebResponse imageList() {
        WebResponse webResponse = new WebResponse<>();
        QueryImageParam param = new QueryImageParam();
        param.setPageSize(PageUtil.getPageSize());
        param.setStartRow(PageUtil.getStartRow());
        AoData aoData = imageService.getImageList(param);
        webResponse.setData(aoData);
        return webResponse;
    }


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


    @RequestMapping("/mark")
    @ResponseBody
    public WebResponse mark(@RequestBody ImageMarkRequest markRequest) {
        WebResponse webResponse = new WebResponse<>();
        categoryService.mark(markRequest);
        return webResponse;
    }


}
