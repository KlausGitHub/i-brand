package com.zhongshang.service;


import com.zhongshang.dto.ConfigDTO;

import java.util.List;

public interface IConfigService {

    Long create(ConfigDTO dto);

    int delete(long id);

    int update(ConfigDTO dto);

    List<ConfigDTO> list(ConfigDTO dto);

    int count(ConfigDTO dto);

    ConfigDTO get(long id);
}