package com.github.carlos.app.controller.console;

import com.github.carlos.common.model.AoData;
import com.github.carlos.common.model.ItemRequest;
import com.github.carlos.common.model.QueryItemParam;
import com.github.carlos.common.model.WebResponse;
import com.github.carlos.common.utils.PageUtil;
import com.github.carlos.dal.bean.ItemInfoDO;
import com.github.carlos.dal.mapper.ItemInfoMapper;
import com.github.carlos.service.ItemImageRelService;
import com.github.carlos.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

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

    @Autowired
    private ItemInfoMapper itemInfoMapper;

    @RequestMapping("/index")
    public String index(Integer itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "console/item/index";
    }


    @RequestMapping("/edit")
    public String edit(Integer itemId, Model model) {
        model.addAttribute("itemId", itemId);
        ItemInfoDO itemInfoDO = itemInfoMapper.findById(itemId);
        model.addAttribute("itemName", itemInfoDO.getItemName());
        return "console/item/edit";
    }

    /**
     * 更新商品信息
     *
     * @param itemRequest
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public WebResponse saveOrUpdate(@RequestBody ItemRequest itemRequest) {
        WebResponse webResponse = new WebResponse<>();
        itemService.saveOrUpdateItem(itemRequest);
        return webResponse;
    }



    @RequestMapping("/itemList")
    @ResponseBody
    public WebResponse itemList(QueryItemParam param) {
        WebResponse webResponse = new WebResponse<>();
        param.setPageSize(PageUtil.getPageSize());
        param.setStartRow(PageUtil.getStartRow());
        AoData aoData = itemService.getPageItemList(param);
        webResponse.setData(aoData);
        return webResponse;
    }


    @RequestMapping("/del")
    @ResponseBody
    public WebResponse del(Integer itemId) {
        WebResponse webResponse = new WebResponse<>();
        itemService.del(itemId);
        return webResponse;
    }

}
