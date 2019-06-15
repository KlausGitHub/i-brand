package com.zhongshang.dao;


import com.zhongshang.model.ApplyDO;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface ApplyDAO {
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insert(ApplyDO record);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertSelective(ApplyDO record);

    ApplyDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplyDO record);

    int updateByPrimaryKey(ApplyDO record);

    List<ApplyDO> selectByCond(ApplyDO cond);

    int countByCond(ApplyDO cond);
}