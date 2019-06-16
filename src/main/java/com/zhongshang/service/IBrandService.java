package com.zhongshang.service;


import com.zhongshang.dto.BrandDTO;

import java.util.List;

public interface IBrandService {

    Long create(BrandDTO dto);

    int delete(Long id);

    int update(BrandDTO dto);

    List<BrandDTO> list(BrandDTO dto);

    List<BrandDTO> listByIds(List<Long> ids);

    int count(BrandDTO dto);

    BrandDTO get(Long id);
}