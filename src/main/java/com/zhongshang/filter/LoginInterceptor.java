package com.zhongshang.filter;

import com.alibaba.fastjson.JSON;
import com.zhongshang.common.BaseResult;
import com.zhongshang.common.ErrorCode;
import com.zhongshang.dto.CustomerDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangsheng
 * @date 2019-05-19
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private String NOT_LOGIN_JSON;

    {
        BaseResult<Void> result = new BaseResult<Void>();
        result.setCode(ErrorCode.COMMON_NOT_LOGIN.getCode());
        result.setMessage(ErrorCode.COMMON_NOT_LOGIN.getErrorMsg());
        NOT_LOGIN_JSON = JSON.toJSONString(result);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURL().indexOf("/index") >= 0 || request.getRequestURL().indexOf("/static/") >= 0){
            return true;
        }
        CustomerDTO customer = (CustomerDTO) request.getSession().getAttribute("customer");
        if (customer == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getOutputStream().write(NOT_LOGIN_JSON.getBytes("UTF8"));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
