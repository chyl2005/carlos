package com.github.carlos.app.controller.console;

import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.ItemRequest;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.service.ItemImageRelService;
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
@RequestMapping("/console/item")
public class ItemRelController {

    @Autowired
    private ItemImageRelService itemImageRelService;


    @RequestMapping("rel/index")
    public String index(Integer itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "console/item/index";
    }


    @RequestMapping("rel/edit")
    public String edit(Integer itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "console/item/edit";
    }

    @RequestMapping("rel/imageList")
    @ResponseBody
    public WebResponse imageList(@RequestParam Integer itemId) {
        WebResponse webResponse = new WebResponse<>();
        AoData aoData = itemImageRelService.getImageList(Arrays.asList(itemId));
        webResponse.setData(aoData);
        return webResponse;
    }


    @RequestMapping("rel/saveOrUpdate")
    @ResponseBody
    public WebResponse saveOrUpdateRel(@RequestBody ItemRequest itemRequest) {
        WebResponse webResponse = new WebResponse<>();
        itemImageRelService.insertOrUpdate(itemRequest);
        return webResponse;
    }

    @RequestMapping("rel/del")
    @ResponseBody
    public WebResponse del(@RequestParam Integer itemId, @RequestParam Integer imageId) {
        WebResponse webResponse = new WebResponse<>();
        itemImageRelService.del(itemId, imageId);
        return webResponse;
    }


}
