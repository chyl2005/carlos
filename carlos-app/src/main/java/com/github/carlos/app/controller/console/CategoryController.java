package com.github.carlos.app.controller.console;

import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.CategoryResponse;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.dal.bean.CategoryDO;
import com.github.carlos.dal.mapper.CategoryMapper;
import com.github.carlos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 13:19
 * @description: TODO
 */
@Controller
@RequestMapping("/console/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;


    @RequestMapping("/index")
    public String index() {
        return "console/category/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public WebResponse getPageList() {
        WebResponse webResponse = new WebResponse<>();
        AoData categoryList = categoryService.getCategoryList();
        webResponse.setData(categoryList);
        return webResponse;
    }


    @RequestMapping("/allCates")
    @ResponseBody
    public WebResponse allCates() {
        WebResponse webResponse = new WebResponse<>();
        List<CategoryDO> categoryDOS = categoryMapper.selectAll();
        webResponse.setData(categoryDOS);
        return webResponse;
    }

    @RequestMapping("/getCategoryList")
    @ResponseBody
    public WebResponse getCategoryList() {
        WebResponse webResponse = new WebResponse<>();
        List<CategoryResponse> categoryDOS = categoryService.getCategorys();
        webResponse.setData(categoryDOS);
        return webResponse;
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public WebResponse insertOrUpdate(@RequestBody CategoryDO categoryDO) {
        WebResponse webResponse = new WebResponse<>();
        categoryService.insertOrUpdate(categoryDO);
        return webResponse;
    }

}
