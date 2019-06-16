package com.zhongshang.dao;


import com.zhongshang.model.BrandDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandDAO {

    int deleteByPrimaryKey(Long id);

    int insert(BrandDO record);

    int insertSelective(BrandDO record);

    BrandDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BrandDO record);

    int updateByPrimaryKey(BrandDO record);

    List<BrandDO> selectByCond(BrandDO cond);

    List<BrandDO> selectByIds(@Param("ids") List<Long> ids);

    int countByCond(BrandDO cond);
}