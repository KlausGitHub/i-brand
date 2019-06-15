package com.zhongshang.dao;


import com.zhongshang.model.ConfigDO;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface ConfigDAO {
    int deleteByPrimaryKey(Long id);

    Long insert(ConfigDO record);

    Long insertSelective(ConfigDO record);

    ConfigDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ConfigDO record);

    int updateByPrimaryKey(ConfigDO record);

    List<ConfigDO> selectByCond(ConfigDO cond);

    int countByCond(ConfigDO cond);
}