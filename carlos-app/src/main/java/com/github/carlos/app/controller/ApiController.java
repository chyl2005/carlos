package com.github.carlos.app.controller;

import com.github.carlos.common.model.CategoryResponse;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.dal.bean.ImageDO;
import com.github.carlos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/29 20:50
 * @description: TODO
 */
@Controller
@RequestMapping("/api")
public class ApiController {


    @Autowired
    private CategoryService categoryService;


    @RequestMapping("/itemDatas")
    @ResponseBody
    public WebResponse itemDatas() {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        List<CategoryResponse> categoryDOS = categoryService.getCategorys();
        webResponse.setData(categoryDOS);
        return webResponse;
    }



    @RequestMapping("/banner")
    @ResponseBody
    public WebResponse banner() {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        List<ImageDO> banners = categoryService.getBanners();
        webResponse.setData(banners);
        return webResponse;
    }


}
