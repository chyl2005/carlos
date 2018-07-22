package com.github.carlos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author:chyl2005
 * Date:17/6/14
 * Time:17:56
 * Desc:描述该类的作用
 */
@Component
public class Config implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    @Value("${loginkey}")
    private String loginkey;
    @Value("${authCode}")
    private String authcode;
    @Value("${host}")
    private String host;
    @Value("${projectName}")
    private String projectName;

    @Value("${env}")
    private String env;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("Config   loginkey={} authcode={} host={}", loginkey, authcode, host);
    }

    public String getLoginkey() {
        return loginkey;
    }

    public String getAuthcode() {
        return authcode;
    }

    public String getHost() {
        return host;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getSuccessUrl() {
        return host + "/" + projectName + "/index/index";
    }

    public String getLoginUrl() {
        return host + "/" + projectName + "/login.html";
    }

    public String getBaseUrl() {
        return host + "/" + projectName;
    }

    public String getEnv() {
        return env;
    }
}
