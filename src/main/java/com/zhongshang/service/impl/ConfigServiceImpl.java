package com.zhongshang.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zhongshang.common.YesOrNoEnum;
import com.zhongshang.dao.ConfigDAO;
import com.zhongshang.dto.ConfigDTO;
import com.zhongshang.model.ConfigDO;
import com.zhongshang.service.IConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConfigServiceImpl implements IConfigService {
    @Resource
    private ConfigDAO dao;

    @Override
    public Long create(ConfigDTO dto) {
        Preconditions.checkArgument(dto != null, "dto不能为空.");
        ConfigDO dataobject = new ConfigDO();
        dataobject.setIsDeleted("N");
        BeanUtils.copyProperties(dto, dataobject);
        dao.insert(dataobject);
        return dataobject.getId();
    }

    @Override
    public int delete(long id) {
        Preconditions.checkArgument(id > 0, "Id必须大于0");
        int cnt = dao.deleteByPrimaryKey(id);
        return cnt;
    }

    @Override
    public int update(ConfigDTO dto) {
        Preconditions.checkArgument(dto != null && dto.getId() != null, "Id不能为空.");
        ConfigDO dataobject = new ConfigDO();
        BeanUtils.copyProperties(dto, dataobject);
        int cnt = dao.updateByPrimaryKeySelective(dataobject);
        return cnt;
    }

    @Override
    public List<ConfigDTO> list(ConfigDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        ConfigDO dataobject = new ConfigDO();
        BeanUtils.copyProperties(dto, dataobject);
        dataobject.setIsDeleted(YesOrNoEnum.NO.getCode());
        List<ConfigDO> dataobjects = dao.selectByCond(dataobject);
        List<ConfigDTO> list = Lists.newArrayList();
        if (dataobjects != null && dataobjects.size() > 0) {
            for (ConfigDO configDO : dataobjects) {
                ConfigDTO configDTO = new ConfigDTO();
                BeanUtils.copyProperties(configDO, configDTO);
                list.add(configDTO);
            }
        }
        return list;
    }

    @Override
    public int count(ConfigDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        ConfigDO dataobject = new ConfigDO();
        BeanUtils.copyProperties(dto, dataobject);
        dataobject.setIsDeleted(YesOrNoEnum.NO.getCode());
        int cnt = dao.countByCond(dataobject);
        return cnt;
    }

    @Override
    public ConfigDTO get(long id) {
        Preconditions.checkArgument(id > 0, "id必须大于0");
        ConfigDO dataobject = dao.selectByPrimaryKey(id);
        if(dataobject == null){
            return null;
        }
        ConfigDTO configDTO = new ConfigDTO();
        BeanUtils.copyProperties(dataobject, configDTO);
        return configDTO;
    }
}