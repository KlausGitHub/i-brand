package com.zhongshang.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zhongshang.common.*;
import com.zhongshang.dao.CustomerDAO;
import com.zhongshang.dto.CustomerDTO;
import com.zhongshang.model.CustomerDO;
import com.zhongshang.request.LoginRequest;
import com.zhongshang.request.RegisterRequest;
import com.zhongshang.response.LoginResponse;
import com.zhongshang.service.EmailService;
import com.zhongshang.service.ICustomerService;
import com.zhongshang.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements ICustomerService {
    @Resource
    private CustomerDAO dao;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource
    private EmailService emailService;
    @Resource
    private HttpServletRequest request;

    @Override
    public Long create(CustomerDTO dto) {
        Preconditions.checkArgument(dto != null, "dto不能为空.");
        CustomerDO model = new CustomerDO();
        BeanUtils.copyProperties(dto, model);
        dao.insert(model);
        return model.getId();
    }

    @Override
    public int delete(Long id) {
        Preconditions.checkArgument(id > 0, "Id必须大于0");
        int cnt = dao.deleteByPrimaryKey(id);
        return cnt;
    }

    @Override
    public int update(CustomerDTO dto) {
        Preconditions.checkArgument(dto != null && dto.getId() != null, "Id不能为空.");
        CustomerDO model = new CustomerDO();
        BeanUtils.copyProperties(dto, model);
        int cnt = dao.updateByPrimaryKeySelective(model);
        return cnt;
    }

    @Override
    public List<CustomerDTO> list(CustomerDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        CustomerDO model = new CustomerDO();
        BeanUtils.copyProperties(dto, model);
        List<CustomerDO> models = dao.selectByCond(model);
        List<CustomerDTO> lists = Lists.newArrayList();
        if (models != null && models.size() > 0) {
            for (CustomerDO bdo : models) {
                CustomerDTO bDto = new CustomerDTO();
                BeanUtils.copyProperties(bdo, bDto);
                lists.add(bDto);
            }
        }
        return lists;
    }

    @Override
    public int count(CustomerDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        CustomerDO model = new CustomerDO();
        BeanUtils.copyProperties(dto, model);
        int cnt = dao.countByCond(model);
        return cnt;
    }

    @Override
    public CustomerDTO get(Long id) {
        Preconditions.checkArgument(id > 0, "id必须大于0");
        CustomerDO model = dao.selectByPrimaryKey(id);
        CustomerDTO result = new CustomerDTO();
        BeanUtils.copyProperties(model, result);
        return result;
    }

    @Override
    public CustomerDTO getByLoginName(LoginRequest request) {
        Preconditions.checkArgument(request != null, "参数不能为空");
        CustomerDO model = dao.selectByLoginName(request);
        CustomerDTO result = new CustomerDTO();
        BeanUtils.copyProperties(model, result);
        return result;
    }

    @Override
    public BaseResult<Boolean> register(RegisterRequest req) {
        String email = req.getEmail();
        CustomerDTO cDto = new CustomerDTO();
        cDto.setEmail(email);
        if (count(cDto) > 0) {
            log.info("该邮箱={}已经存在，请确认", email);
            return ResultUtils.fail(ErrorCode.REGISTER_EMAIL_EXIST_ERROR, Boolean.FALSE);
        }
        String code = UUID.randomUUID().toString();
        CustomerDO model = buildCustomerDO(req, code);
        dao.insert(model);
        //起个线程去发邮件
        sendRegisterEmail(email, code);
        return ResultUtils.success(model.getId() > 0);
    }

    @Override
    public BaseResult<Boolean> activate(String code) {
        CustomerDO cDo = new CustomerDO();
        cDo.setActivationCode(code);
        List<CustomerDO> list = dao.selectByCond(cDo);
        if (list == null || list.size() < 1) {
            return ResultUtils.fail(ErrorCode.REGISTER_CODE_NOT_EXIST_ERROR, Boolean.FALSE);
        }
        CustomerDO customer = list.get(0);
        customer.setDisableFlag(YesOrNoEnum.NO.getCode());
        int cnt = dao.updateByPrimaryKeySelective(customer);
        return ResultUtils.success(cnt > 0);
    }


    @Override
    public BaseResult<LoginResponse> login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        log.info("用户登录请求开始,请求参数={}", JSON.toJSONString(loginRequest));
        String loginName = loginRequest.getLoginName();
        String password = loginRequest.getPassword();
        String md5Pwd = DigestUtils.md5DigestAsHex((password + BrandConstant.PWD_KEY).getBytes());
        loginRequest.setPassword(md5Pwd);
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
        CustomerDTO dbCustomer = getByLoginName(loginRequest);
        if (dbCustomer == null) {
            log.error("未查询到该用户，登录用户信息={}", JSON.toJSONString(loginRequest));
            return ResultUtils.fail(ErrorCode.LOGIN_NAME_OR_PWD_ERROR, loginResponse);
        }
        //更新最后登录时间
        dbCustomer.setLastLoginTime(DateTime.now().toDate());
        update(dbCustomer);
        request.getSession().setAttribute("customer", dbCustomer);
        loginResponse.setCustomerId(dbCustomer.getId());
        loginResponse.setMobile(dbCustomer.getMobile());
        return ResultUtils.success(loginResponse);
    }

    private void sendRegisterEmail(String email, String code) {
        taskExecutor.execute(() -> {
            log.info("注册发送邮件线程开始，邮箱={}", email);
            try {
                emailService.send(email, null, "注册验证", "亲爱的用户：\n" +
                        "您好！感谢您使用北京中商企研企业邮箱注册服务，您正在进行邮箱验证，请点击以下链接完成验证：\n" +
                        "http://localhost:8080/v1/customer/activate?code=" + code, null);
                log.info("邮件发送成功");
            } catch (Exception e) {
                log.error("邮箱发送失败", e);
            }
        });
    }

    private CustomerDO buildCustomerDO(RegisterRequest req, String code) {
        CustomerDO model = new CustomerDO();
        model.setEmail(req.getEmail());
        String pwd = req.getPassword() + BrandConstant.PWD_KEY;
        model.setPassword(DigestUtils.md5DigestAsHex(pwd.getBytes()));
        model.setDisableFlag(YesOrNoEnum.YES.getCode());
        model.setVipFlag(YesOrNoEnum.NO.getCode());
        model.setBalance(BigDecimal.ZERO);
        model.setCreateTime(DateTime.now().toDate());
        model.setActivationCode(code);
        return model;
    }
}