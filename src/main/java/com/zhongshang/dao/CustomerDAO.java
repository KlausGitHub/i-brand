package com.zhongshang.dao;


import com.zhongshang.model.CustomerDO;
import com.zhongshang.request.LoginRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerDAO {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerDO record);

    int insertSelective(CustomerDO record);

    CustomerDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerDO record);

    int updateByPrimaryKey(CustomerDO record);

    List<CustomerDO> selectByCond(CustomerDO cond);

    List<CustomerDO> selectByIds(@Param("ids") List<Long> ids);

    int countByCond(CustomerDO cond);

    CustomerDO selectByLoginName(LoginRequest request);
}