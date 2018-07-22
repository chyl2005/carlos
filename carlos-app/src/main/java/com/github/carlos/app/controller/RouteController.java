package com.github.carlos.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 17:24
 * @description: TODO
 */
@Controller
@RequestMapping("/")
public class RouteController {


    @RequestMapping("/index")
    public String index() {

        return "front/index";
    }

    @RequestMapping("/about")
    public String about() {

        return "front/about";
    }
    @RequestMapping("/news")
    public String news() {

        return "front/news";
    }
    @RequestMapping("/contact")
    public String contact() {

        return "front/contact";
    }


    @RequestMapping("/items")
    public String items() {

        return "front/items";
    }

    @RequestMapping("/itemList")
    public String itemList() {

        return "front/itemList";
    }
}
