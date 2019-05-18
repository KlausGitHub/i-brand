package com.zhongshang.web;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.zhongshang.common.BaseResult;
import com.zhongshang.common.ErrorCode;
import com.zhongshang.common.ResultUtils;
import com.zhongshang.dto.CustomerDTO;
import com.zhongshang.request.LoginRequest;
import com.zhongshang.request.RegisterRequest;
import com.zhongshang.request.UpdateRequest;
import com.zhongshang.response.LoginResponse;
import com.zhongshang.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yangsheng
 * @date 2019-05-12
 */
@Slf4j
@RestController
@RequestMapping("/v1/customer/")
public class CustomerController {

    @Resource
    private ICustomerService customerService;

    /**
     * App用户登录
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResult<LoginResponse> login(@RequestBody LoginRequest req) {
        LoginResponse result = new LoginResponse();
        try {
            String errorMsg = checkParam(req);
            if (errorMsg != null) {
                return ResultUtils.fail(ErrorCode.COMMON_MY_ERR, result, errorMsg);
            }
            return customerService.login(req);
        } catch (Exception e) {
            log.error("login error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.LOGIN_SYS_ERROR, result);
        }
    }

    /**
     * App用户注册
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseResult<Boolean> register(@RequestBody RegisterRequest req) {
        try {
            log.info("注册请求开始，参数={}", JSON.toJSONString(req));
            return customerService.register(req);
        } catch (Exception e) {
            log.error("register error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.REGISTER_ERROR, Boolean.FALSE);
        }
    }

    /**
     * App用户邮箱激活
     *
     * @return
     */
    @RequestMapping(value = "activate", method = RequestMethod.GET)
    public BaseResult<Boolean> activate(HttpServletRequest request) {
        try {
            String code = request.getParameter("code");
            log.info("邮箱激活请求开始，code={}", code);
            Preconditions.checkNotNull(StringUtils.isNotBlank(code));
            return customerService.activate(code);
        } catch (Exception e) {
            log.error("email activate error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.REGISTER_ACTIVATE_ERROR, Boolean.FALSE, e.getMessage());
        }
    }

    /**
     * 完善信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<Boolean> update(@RequestBody UpdateRequest req) {
        try {
            log.info("设置用户信息请求开始, 请求参数={}", JSON.toJSONString(req));
            CustomerDTO cDto = customerService.get(req.getCustomerId());
            if (cDto == null) {
                log.error("未查询到该用户信息，请求用户id={}", req.getCustomerId());
                return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, Boolean.FALSE, "未查询到该用户");
            }
            cDto.setLoginName(req.getLoginName());
            cDto.setMobile(req.getMobile());
            cDto.setHeadLogo(req.getHeadLogo());
            return ResultUtils.success(customerService.update(cDto) > 0);
        } catch (Exception e) {
            log.error("update customer error, caused by = {}", e);
            return ResultUtils.fail(ErrorCode.UPDATE_CUSTOMER_ERROR, Boolean.FALSE);
        }
    }


    private String checkParam(LoginRequest req) {
        if (StringUtils.isEmpty(req.getLoginName())) {
            return "用户名不能为空";
        }
        if (StringUtils.isEmpty(req.getPassword())) {
            return "密码不能为空";
        }
        return null;
    }
}
