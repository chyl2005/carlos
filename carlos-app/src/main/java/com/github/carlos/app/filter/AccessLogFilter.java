package com.github.carlos.app.filter;

import com.github.carlos.common.constant.SysConstant;
import com.github.carlos.common.utils.JsonUtils;
import com.github.carlos.common.utils.ThreadLocalContext;
import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/17 12:15
 * @description: 过滤的请求会保存访问日志,转化异常
 */
public class AccessLogFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        String result = "";
        String body = "";
        boolean success = true;
        Stopwatch stopwatch = Stopwatch.createStarted();
        ThreadLocalContext.set(SysConstant.PARAMSMAP, request.getParameterMap());
        try {
            AccessHttpServletRequestWrapper requestWrapper = new AccessHttpServletRequestWrapper(request);
            // 传文件不记录请求参数
            String contentType = requestWrapper.getContentType();
            if (StringUtils.isNotBlank(contentType) && !contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                body = requestWrapper.readBody();
                ThreadLocalContext.set(SysConstant.PARAMS, body);
            }
            filterChain.doFilter(requestWrapper, response);
        } catch (Exception e) {
            success = false;
            LOGGER.error(
                    "INVOKE->{} ,parameterMap:{} , METHOD:{}, REQUEST:{} ,EXTRAPARAMS:{}, RESPONSE:{},STATUS:{} , IME COST:{}ms",
                    request.getRequestURI(), JsonUtils.object2Json(request.getParameterMap()), request.getMethod(),
                    ThreadLocalContext.get(SysConstant.PARAMSMAP),
                    JsonUtils.object2Json(ThreadLocalContext.get(SysConstant.PARAMSMAP)), result, success,
                    stopwatch.elapsed(TimeUnit.MILLISECONDS), e);
        } finally {
            long cost = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            LOGGER.info(
                    "INVOKE->{} ,parameterMap:{} , METHOD:{}, REQUEST:{} ,EXTRAPARAMS:{}, RESPONSE:{},STATUS:{} , IME COST:{}ms",
                    request.getRequestURI(), JsonUtils.object2Json(request.getParameterMap()), request.getMethod(),
                    ThreadLocalContext.get(SysConstant.PARAMSMAP),
                    JsonUtils.object2Json(ThreadLocalContext.get(SysConstant.PARAMSMAP)), result, success,
                    cost);
            ThreadLocalContext.clear();
        }

    }
}
