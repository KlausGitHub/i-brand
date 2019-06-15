package com.zhongshang.dao;

import com.zhongshang.model.ServiceDO;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface ServiceDAO {
    int deleteByPrimaryKey(Long id);

    Long insert(ServiceDO record);

    Long insertSelective(ServiceDO record);

    ServiceDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServiceDO record);

    int updateByPrimaryKey(ServiceDO record);

    List<ServiceDO> selectByCond(ServiceDO cond);

    int countByCond(ServiceDO cond);
}