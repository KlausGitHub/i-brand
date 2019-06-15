package com.zhongshang.service;


import com.zhongshang.dto.ServiceDTO;

import java.util.List;

public interface IServiceService {
    Long create(ServiceDTO dto);

    int delete(long id);

    int update(ServiceDTO dto);

    List<ServiceDTO> list(ServiceDTO dto);

    int count(ServiceDTO dto);

    ServiceDTO get(long id);
}