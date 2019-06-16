package com.zhongshang.dao;


import com.zhongshang.model.PatentDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PatentDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PatentDO record);

    int insertSelective(PatentDO record);

    PatentDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PatentDO record);

    int updateByPrimaryKey(PatentDO record);

    List<PatentDO> selectByCond(PatentDO cond);

    List<PatentDO> selectByIds(@Param("ids") List<Long> ids);

    int countByCond(PatentDO cond);
}