package com.zhongshang.service;


import com.zhongshang.common.BaseResult;
import com.zhongshang.dto.CustomerDTO;
import com.zhongshang.request.LoginRequest;
import com.zhongshang.request.RegisterRequest;
import com.zhongshang.response.LoginResponse;

import java.util.List;

public interface ICustomerService {

    Long create(CustomerDTO dto);

    int delete(Long id);

    int update(CustomerDTO dto);

    List<CustomerDTO> list(CustomerDTO dto);

    int count(CustomerDTO dto);

    CustomerDTO get(Long id);

    CustomerDTO getByLoginName(LoginRequest request);

    BaseResult<Boolean> register(RegisterRequest req);

    BaseResult<Boolean> activate(String code);

    BaseResult<LoginResponse> login(LoginRequest request);
}