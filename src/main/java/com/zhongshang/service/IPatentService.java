package com.zhongshang.service;


import com.zhongshang.dto.PatentDTO;

import java.util.List;

public interface IPatentService {
    Long create(PatentDTO dto);

    int delete(Long id);

    int update(PatentDTO dto);

    List<PatentDTO> list(PatentDTO dto);

    List<PatentDTO> listByIds(List<Long> ids);

    int count(PatentDTO dto);

    PatentDTO get(Long id);
}