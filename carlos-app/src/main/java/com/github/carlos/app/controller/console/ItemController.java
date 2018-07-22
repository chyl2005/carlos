package com.github.carlos.app.controller.console;

import com.github.carlos.common.model.ItemRequest;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 13:19
 * @description: 产品保存
 */
@Controller
@RequestMapping("/console/item")
public class ItemController {

    @Autowired
    private ItemService itemService;


    /**
     * 更新商品信息
     *
     * @param itemRequest
     * @return
     */
    @RequestMapping("/saveOrUpdateItem")
    @ResponseBody
    public WebResponse saveOrUpdateItem(@RequestBody ItemRequest itemRequest) {
        WebResponse webResponse = new WebResponse<>();
        itemService.saveOrUpdateItem(itemRequest);
        return webResponse;
    }

}
