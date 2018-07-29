package com.github.carlos.app.interceptor;


import com.github.carlos.common.constant.SysConstant;
import com.github.carlos.common.utils.JsonUtils;
import com.github.carlos.common.utils.ThreadLocalContext;
import com.github.carlos.common.utils.UserUtils;
import com.github.carlos.dal.bean.MenuDO;
import com.github.carlos.service.ModuleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author chenyanlong
 * @ClassName: CheckPermissionInterceptor
 * @Description: 权限校验拦截器 后于登录拦截器执行
 * @date 2015年11月23日 下午5:37:47
 */
@Component
public class CheckPermissionInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckPermissionInterceptor.class);
    @Autowired
    private ModuleService moduleService;

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取当前登录用户信息
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //Method method = handlerMethod.getMethod();
            // 方法名
            RequestMapping requestMapping = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequestMapping.class);
            String[] actionPaths = requestMapping.value();
            // 当前请求 的controller requestMapping 中的值 也就是 Authority 表中 的 action_path
            String actionPath = actionPaths[0];
            // 查询是否有权限
            //去掉 mapping 前后"/"
            if (StringUtils.isNotBlank(actionPath) && !actionPath.equals("/")) {
                actionPath = actionPath.startsWith("/") ? actionPath.substring(1, actionPath.length()) : actionPath;
                actionPath = actionPath.endsWith("/") ? actionPath.substring(0, actionPath.length() - 1) : actionPath;
            }
            ThreadLocalContext.set(SysConstant.EXTRA_PARAMS, UserUtils.getUser());
            ThreadLocalContext.set(SysConstant.PARAMSMAP, request.getParameterMap());
            MenuDO  menuDO= this.moduleService.getModuleByPath(actionPath);
            if (null != menuDO) {
                Integer moduleId = menuDO.getId();
                // 用户拥有权限的模块
                Set<Integer> moduleIds = UserUtils.getMenuIds();
                if (moduleIds.contains(moduleId)) {
                    return true;
                }
            }
            LOGGER.info("INVOKE->{} ,parameterMap:{} , METHOD:{}, REQUEST:{} ,EXTRAPARAMS:{}, IME COST:{}ms"
                    , request.getRequestURI()
                    , JsonUtils.object2Json(ThreadLocalContext.get(SysConstant.PARAMSMAP))
                    , request.getMethod()
                    , ThreadLocalContext.get(SysConstant.PARAMS)
                    , JsonUtils.object2Json(ThreadLocalContext.get(SysConstant.EXTRA_PARAMS)));
            request.getRequestDispatcher("/noPermission.html").forward(request, response);
            return false;
        }

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {


    }

}
