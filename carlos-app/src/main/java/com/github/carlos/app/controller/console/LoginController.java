package com.github.carlos.app.controller.console;


import com.github.carlos.common.model.WebResponse;
import com.github.carlos.common.utils.MD5Utils;
import com.github.carlos.common.utils.ValidateCode;
import com.github.carlos.dal.bean.UserDO;
import com.github.carlos.service.Config;
import com.github.carlos.service.UserService;
import com.github.carlos.service.LoginInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author chyl
 * @Description:
 * @ClassName: LoginController
 * @date 2015年12月21日 上午10:10:09
 */
@Controller
@RequestMapping("/console/login")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private static final Integer LOGIN_CACHE_TIME = 3600 * 24;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private Config config;
    /**
     * @param password
     * @param request
     * @param response
     * @return String 返回类型
     * @throws IOException
     * @Description: 登录校验
     * @author chenyanlong
     * @date 2015年11月30日 下午3:27:35
     */
    @RequestMapping("/isLogin")
    @ResponseBody
    public WebResponse isLogin(String userName, String password, String code, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        String host = request.getServerName();
        LOGGER.info("ServerName-------------" + host);
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            WebResponse webResponse = WebResponse.getParamErrorWebResponse();
            webResponse.setMessage("用户名或者密码错误！");
            return webResponse;
        }
        //        if (StringUtils.isBlank(code)) {
        //            WebResponse webResponse = WebResponse.getParamErrorWebResponse();
        //            webResponse.setMessage("验证码不能为空！");
        //            return webResponse;
        //        }
        // 获取验证码
        //        String codeValue = CookieUtils.getCookieValue(request, authcode);
        //        String md5 = MD5Utils.getMD5(code.toLowerCase());
        //        if (!md5.equals(codeValue)) {
        //            WebResponse webResponse = WebResponse.getParamErrorWebResponse();
        //            webResponse.setMessage("验证码错误！");
        //            return webResponse;
        //        }
        // 加密
        String pwd = MD5Utils.getMD5(userName + password);
        UserDO entity = this.userService.login(userName, pwd);
        if (entity == null) {
            WebResponse webResponse = WebResponse.getParamErrorWebResponse();
            webResponse.setMessage("用户名或者密码错误！");
            return webResponse;
        }
        //保存登录信息
        loginInfoService.save(pwd, entity.getId());

        // 密码校验成功 添加cookie
        Cookie mylogincookie = new Cookie(config.getLoginkey(), pwd);
        mylogincookie.setMaxAge(LOGIN_CACHE_TIME);
        mylogincookie.setPath("/");
        mylogincookie.setDomain(host);

        response.addCookie(mylogincookie);
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        webResponse.setData(config.getSuccessUrl());
        return webResponse;
    }

    /**
     * @param request
     * @param response
     * @param @throws  IOException
     * @return Boolean 返回类型
     * @Description: 退出
     * @author chenyanlong
     * @date 2015年11月30日 下午6:21:35
     */
    @RequestMapping("/logout")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取所有的cookie
        Cookie[] cookies = request.getCookies();
        String logincookie = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                // 判断是否有该cookie
                if (cookie.getName().equals(config.getLoginkey())) {
                    logincookie = cookie.getValue();
                    cookie.setMaxAge(0);
                }
                if (cookie.getName().equals(config.getAuthcode())) {
                    cookie.setMaxAge(0);
                }
                response.addCookie(cookie);
            }
        }
        if (logincookie != null) {
            this.loginInfoService.del(logincookie);
        }
        response.sendRedirect(config.getLoginUrl());
    }

    /**
     * @return String 返回类型
     * @Description: 无权限页面跳转
     * @author chenyanlong
     * @date 2015年12月11日 下午1:11:52
     */
    @RequestMapping("/noPermissionPage")
    public String noPermissionPage() {
        return "admin/noPermission";
    }

    /**
     * @return void 返回类型
     * @Description: 验证码
     * @author chenyanlong
     * @date 2015年11月25日 下午1:43:06
     */
    @RequestMapping("/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) {
        String host = request.getServerName();
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ValidateCode vCode = new ValidateCode(106, 52);
        String codeValue = vCode.getCode();
        LOGGER.info("ServerName-------------" + host);

        // 加密过程
        String md5 = MD5Utils.getMD5(codeValue.toLowerCase());
        Cookie cookie = new Cookie(config.getAuthcode(), md5);
        cookie.setPath("/");
        cookie.setMaxAge(300);
        cookie.setDomain(host);
        response.addCookie(cookie);
        try {
            vCode.write(response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param user
     * @return Boolean 返回类型
     * @Description: 注册
     * @author chenyanlong
     * @date 2015年11月26日 上午9:15:59
     */
    @RequestMapping("/register")
    @ResponseBody
    public WebResponse register(UserDO user) {
        WebResponse webResponse = WebResponse.getSuccessWebResponse();
        user.setGmtModified(new Date());
        user.setGmtCreated(new Date());
        String password = user.getPassword();
        String username = user.getUserName();
        String pwd = MD5Utils.getMD5(username + password);
        user.setPassword(pwd);
        this.userService.saveOrUpdate(user);
        return webResponse;

    }

}
