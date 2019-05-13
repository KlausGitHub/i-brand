package com.zhongshang.dao;


import com.zhongshang.model.CustomerDO;

import java.util.List;

public interface CustomerDAO {
    int deleteByPrimaryKey(Long id);

    Long insert(CustomerDO record);

    Long insertSelective(CustomerDO record);

    CustomerDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerDO record);

    int updateByPrimaryKey(CustomerDO record);

    List<CustomerDO> selectByCond(CustomerDO cond);

    int countByCond(CustomerDO cond);
}