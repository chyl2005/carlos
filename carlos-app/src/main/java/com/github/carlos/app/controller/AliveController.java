package com.github.carlos.app.controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:chyl2005
 * Date:17/3/26
 * Time:10:12
 * Desc:描述该类的作用
 */
@Controller
@RequestMapping("/api/monitor")
public class AliveController {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AliveController.class);


    /**
     * alive接口，检查服务是否正常
     *
     * @return
     */
    @RequestMapping("/alive")
    @ResponseBody
    public Map<String, Object> monitorAlive() {
        LOGGER.info("alive");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "ok");
        return result;
    }

}
