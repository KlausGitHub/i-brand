package com.zhongshang.dao;


import com.zhongshang.model.BrandDO;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface BrandDAO {

    int deleteByPrimaryKey(Long id);

    Long insert(BrandDO record);

    Long insertSelective(BrandDO record);

    BrandDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BrandDO record);

    int updateByPrimaryKey(BrandDO record);

    List<BrandDO> selectByCond(BrandDO cond);

    int countByCond(BrandDO cond);
}