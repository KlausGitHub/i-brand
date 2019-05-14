package com.zhongshang.dao;


import com.zhongshang.model.PatentDO;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface PatentDAO {
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insert(PatentDO record);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertSelective(PatentDO record);

    PatentDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PatentDO record);

    int updateByPrimaryKey(PatentDO record);

    List<PatentDO> selectByCond(PatentDO cond);

    int countByCond(PatentDO cond);
}