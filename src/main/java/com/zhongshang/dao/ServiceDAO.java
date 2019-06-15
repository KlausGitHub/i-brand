package com.zhongshang.dao;

import com.zhongshang.model.ServiceDO;

import java.util.List;

public interface ServiceDAO {
    int deleteByPrimaryKey(Long id);

    int insert(ServiceDO record);

    int insertSelective(ServiceDO record);

    ServiceDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServiceDO record);

    int updateByPrimaryKey(ServiceDO record);

    List<ServiceDO> selectByCond(ServiceDO cond);

    int countByCond(ServiceDO cond);
}