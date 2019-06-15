package com.zhongshang.dao;


import com.zhongshang.model.PatentDO;

import java.util.List;

public interface PatentDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PatentDO record);

    int insertSelective(PatentDO record);

    PatentDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PatentDO record);

    int updateByPrimaryKey(PatentDO record);

    List<PatentDO> selectByCond(PatentDO cond);

    int countByCond(PatentDO cond);
}