package com.zhongshang.web;

import com.zhongshang.common.BaseResult;
import com.zhongshang.request.LoginRequest;
import com.zhongshang.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public BaseResult<Boolean> login(@RequestBody LoginRequest req) {
        return null;
    }

    /**
     * App用户注册
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseResult<Boolean> register(@RequestBody LoginRequest req) {
        return null;
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public String get(Long id) {
        return customerService.get(id).getEmail();
    }
}
