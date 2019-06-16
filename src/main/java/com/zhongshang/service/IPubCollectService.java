package com.zhongshang.service;


import com.zhongshang.dto.PubCollectDTO;

import java.util.List;

public interface IPubCollectService {
    Long create(PubCollectDTO dto);

    int delete(long id);

    int update(PubCollectDTO dto);

    List<PubCollectDTO> list(PubCollectDTO dto);

    int count(PubCollectDTO dto);

    PubCollectDTO get(long id);
}