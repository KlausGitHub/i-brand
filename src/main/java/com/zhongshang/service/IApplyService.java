package com.zhongshang.service;

import com.zhongshang.dto.ApplyDTO;

import java.util.List;

public interface IApplyService {

    long create(ApplyDTO dto);

    int delete(long id);

    int update(ApplyDTO dto);

    List<ApplyDTO> list(ApplyDTO dto);

    int count(ApplyDTO dto);

    ApplyDTO get(long id);
}