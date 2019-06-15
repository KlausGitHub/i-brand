package com.zhongshang.dao;


import com.zhongshang.model.ApplyDO;

import java.util.List;

public interface ApplyDAO {
    int deleteByPrimaryKey(Long id);

    int insert(ApplyDO record);

    int insertSelective(ApplyDO record);

    ApplyDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyDO record);

    int updateByPrimaryKey(ApplyDO record);

    List<ApplyDO> selectByCond(ApplyDO cond);

    int countByCond(ApplyDO cond);
}