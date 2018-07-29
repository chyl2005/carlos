package com.github.carlos.service.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/25 12:05
 * @description: TODO
 */
@Service
public class Handler2 implements AbsHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(Handler2.class);


    @Override
    public void handle() {
        LOGGER.info("Handler2");
    }
}
