package com.zhongshang.dao;


import com.zhongshang.model.ConfigDO;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface ConfigDAO {
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insert(ConfigDO record);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertSelective(ConfigDO record);

    ConfigDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ConfigDO record);

    int updateByPrimaryKey(ConfigDO record);

    List<ConfigDO> selectByCond(ConfigDO cond);

    int countByCond(ConfigDO cond);
}