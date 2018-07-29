package com.github.carlos.app.interceptor;


import com.github.carlos.common.constant.SysConstant;
import com.github.carlos.common.model.User;
import com.github.carlos.common.model.UserInfoCacheVo;
import com.github.carlos.common.utils.CookieUtils;
import com.github.carlos.common.utils.PageUtil;
import com.github.carlos.common.utils.ThreadLocalContext;
import com.github.carlos.common.utils.UserUtils;
import com.github.carlos.service.Config;
import com.github.carlos.service.LoginInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author:chyl2005
 * Date:17/6/10
 * Time:22:46
 * Desc:描述该类的作用
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
    @Autowired
    private Config config;

    @Autowired
    private LoginInfoService loginInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String url = request.getRequestURL().toString();
        String login = CookieUtils.getCookieValue(request, config.getLoginkey());
        UserInfoCacheVo userInfoCache = this.loginInfoService.getLoginInfo(login);
        if (userInfoCache == null || CollectionUtils.isEmpty(userInfoCache.getMenus())) {
            response.sendRedirect(config.getLoginUrl());
            return false;
        }

        String startRow = request.getParameter("startRow");
        if (StringUtils.isNotBlank(startRow)) {
            PageUtil.setStartRow(Integer.parseInt(startRow));
        }
        String ps = request.getParameter("pageSize");
        //请求中有传入pageSize
        if (StringUtils.isNotBlank(ps)) {
            PageUtil.setPageSize(Integer.parseInt(ps));
        }

        User user = userInfoCache.getUser();
        UserUtils.bind(user);
        UserUtils.bind(userInfoCache.getMenus());
        UserUtils.bind(userInfoCache.getMenuIds());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
        UserUtils.unbindUser();
        UserUtils.unbindUserMenus();
        UserUtils.unbindMenuIds();

    }


}
