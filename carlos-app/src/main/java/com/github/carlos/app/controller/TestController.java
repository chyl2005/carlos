package com.github.carlos.app.controller;

import com.github.carlos.common.model.WebResponse;
import com.github.carlos.service.handler.AbsHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/16 23:18
 * @description: TODO
 */

@Controller
@RequestMapping("/api")
public class TestController {


    @Resource(name = "hanlders")
    private List<AbsHandler> handlers;


    @RequestMapping("/hanlders")
    @ResponseBody
    public WebResponse hanlders() {
        WebResponse webResponse = new WebResponse();
        for (AbsHandler handler : handlers) {
            handler.handle();
        }
        return webResponse;
    }

}
