package com.zhongshang.dao;


import com.zhongshang.model.ConfigDO;

import java.util.List;

public interface ConfigDAO {
    int deleteByPrimaryKey(Long id);

    int insert(ConfigDO record);

    int insertSelective(ConfigDO record);

    ConfigDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ConfigDO record);

    int updateByPrimaryKey(ConfigDO record);

    List<ConfigDO> selectByCond(ConfigDO cond);

    int countByCond(ConfigDO cond);
}