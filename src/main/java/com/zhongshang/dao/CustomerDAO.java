package com.zhongshang.dao;


import com.zhongshang.model.CustomerDO;
import com.zhongshang.request.LoginRequest;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface CustomerDAO {
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insert(CustomerDO record);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertSelective(CustomerDO record);

    CustomerDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerDO record);

    int updateByPrimaryKey(CustomerDO record);

    List<CustomerDO> selectByCond(CustomerDO cond);

    int countByCond(CustomerDO cond);

    CustomerDO selectByLoginName(LoginRequest request);
}