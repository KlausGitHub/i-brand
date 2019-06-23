package com.zhongshang.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.zhongshang.common.BaseResult;
import com.zhongshang.common.BrandConstant;
import com.zhongshang.common.ErrorCode;
import com.zhongshang.common.ResultUtils;
import com.zhongshang.dto.CustomerDTO;
import com.zhongshang.request.ChangePwdRequest;
import com.zhongshang.request.LoginRequest;
import com.zhongshang.request.RegisterRequest;
import com.zhongshang.request.UpdateRequest;
import com.zhongshang.response.LoginResponse;
import com.zhongshang.service.ICustomerService;
import com.zhongshang.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public BaseResult<Boolean> activate(HttpServletRequest request, HttpServletResponse response) {
        try {
            String code = request.getParameter("code");
            log.info("邮箱激活请求开始，code={}", code);
            Preconditions.checkNotNull(StringUtils.isNotBlank(code), "邮箱激活码不能为空");
            BaseResult<Boolean> result = customerService.activate(code);
            if (result != null && result.success()) {
                //激活成功
                response.sendRedirect("https://www.baidu.com");
            } else {
                response.sendRedirect("https://www.baidu.com");
            }
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

    /**
     * 根据邮箱/手机号/登录名查询用户是否存在
     *
     * @param loginName
     * @return
     */
    @RequestMapping(value = "find", method = RequestMethod.GET)
    public BaseResult<Boolean> find(@RequestParam("loginName") String loginName) {
        log.info("查询用户信息是否存在，参数={}", loginName);
        if (StringUtils.isEmpty(loginName)) {
            return ResultUtils.fail(ErrorCode.COMMON_NOT_EMPTY_ERR, false, "查询条件");
        }
        LoginRequest loginRequest = new LoginRequest();
        if (RegexUtils.phoneRegex(loginName)) {
            //手机号码登录
            loginRequest.setLoginType(BrandConstant.PHONE_TYPE);
        } else if (RegexUtils.emailRegex(loginName)) {
            //邮箱登录
            loginRequest.setLoginType(BrandConstant.EMAIL_TYPE);
        } else {
            //用户名登录
            loginRequest.setLoginType(BrandConstant.LOGIN_NAME_TYPE);
        }
        loginRequest.setLoginName(loginName);
        CustomerDTO dbCustomer = customerService.getByLoginName(loginRequest);
        if (dbCustomer != null) {
            return ResultUtils.success(true);
        }
        return ResultUtils.fail(ErrorCode.CUSTOMER_NOT_EXISTS, false);
    }

    /**
     * 重置密码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "changePwd", method = RequestMethod.POST)
    public BaseResult<Boolean> changePwd(@RequestBody ChangePwdRequest req) {
        try {
            log.info("重置密码请求开始,请求参数={}", JSON.toJSONString(req));
            Preconditions.checkNotNull(req.getCustomerId(), "客户id不能为空");
            Preconditions.checkNotNull(req.getOldPwd(), "原始密码不能为空");
            Preconditions.checkNotNull(req.getNewPwd(), "新密码不能为空");
            CustomerDTO cDto = customerService.get(req.getCustomerId());
            if (cDto == null) {
                log.error("修改密码时未查询到用户信息，请求用户id={}", req.getCustomerId());
                return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, Boolean.FALSE, "未查询到该用户");
            }
            String oldPwd = req.getOldPwd() + BrandConstant.PWD_KEY;
            if (!cDto.getPassword().equals(DigestUtils.md5DigestAsHex(oldPwd.getBytes()))) {
                return ResultUtils.fail(ErrorCode.CHANGE_OLD_PASSWORD_ERROR, false);
            }
            String newPwd = req.getNewPwd() + BrandConstant.PWD_KEY;
            cDto.setPassword(DigestUtils.md5DigestAsHex(newPwd.getBytes()));
            return ResultUtils.success(customerService.update(cDto) > 0);
        } catch (Exception e) {
            log.error("change customer password error, caused by = ", e);
            return ResultUtils.fail(ErrorCode.CHANGE_PASSWORD_ERROR, false, e.getMessage());
        }
    }

    /**
     * 查询会员信息列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public BaseResult<JSONObject> list(@RequestBody JSONObject req) {
        log.info("查询会员列表请求开始,请求参数={}", JSON.toJSONString(req));
        JSONObject json = new JSONObject();
        try {
            CustomerDTO cDto = new CustomerDTO();
            BeanUtils.copyProperties(req, cDto);
            int pageNum = req.getInteger("pageNum");
            int pageSize = req.getInteger("pageSize");
            Page page = PageHelper.startPage(pageNum > 0 ? pageNum : 1, pageSize > 0 ? pageSize : 10, true);
            List<CustomerDTO> list = customerService.list(cDto);
            json.put("pageNum", pageNum);
            json.put("pageSize", pageSize);
            json.put("total", page.getTotal());
            json.put("result", list);
            return ResultUtils.success(json);
        } catch (Exception e) {
            log.error("查询会员信息失败", e);
            return ResultUtils.fail(ErrorCode.SYS_RUN_EXCEPTION, json, "会员列表查询");
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
