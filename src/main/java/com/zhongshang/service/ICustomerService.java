package com.zhongshang.service;


import com.zhongshang.dto.CustomerDTO;

import java.util.List;

public interface ICustomerService {
    Long create(CustomerDTO dto);

    int delete(Long id);

    int update(CustomerDTO dto);

    List<CustomerDTO> list(CustomerDTO dto);

    int count(CustomerDTO dto);

    CustomerDTO get(Long id);
}