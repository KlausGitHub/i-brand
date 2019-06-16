package com.zhongshang.dao;


import com.zhongshang.model.PubCollectDO;

import java.util.List;

public interface PubCollectDAO {
    int deleteByPrimaryKey(Long id);

    int insert(PubCollectDO record);

    PubCollectDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PubCollectDO record);

    int updateByPrimaryKey(PubCollectDO record);

    List<PubCollectDO> selectByCond(PubCollectDO cond);

    int countByCond(PubCollectDO cond);
}