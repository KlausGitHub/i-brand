package com.zhongshang.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zhongshang.dao.ServiceDAO;
import com.zhongshang.dto.ServiceDTO;
import com.zhongshang.model.ServiceDO;
import com.zhongshang.service.IServiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ServiceServiceImpl implements IServiceService {
    @Resource
    private ServiceDAO dao;

    @Override
    public Long create(ServiceDTO dto) {
        Preconditions.checkArgument(dto != null, "dto不能为空.");
        ServiceDO dataobject = new ServiceDO();
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
    public int update(ServiceDTO dto) {
        Preconditions.checkArgument(dto != null && dto.getId() != null, "Id不能为空.");
        ServiceDO dataobject = new ServiceDO();
        BeanUtils.copyProperties(dto, dataobject);
        int cnt = dao.updateByPrimaryKeySelective(dataobject);
        return cnt;
    }

    @Override
    public List<ServiceDTO> list(ServiceDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        ServiceDO dataobject = new ServiceDO();
        BeanUtils.copyProperties(dto, dataobject);
        List<ServiceDO> dataobjects = dao.selectByCond(dataobject);
        List<ServiceDTO> list = Lists.newArrayList();
        if (dataobjects != null && dataobjects.size() > 0) {
            for (ServiceDO serviceDO : dataobjects) {
                ServiceDTO serviceDTO = new ServiceDTO();
                BeanUtils.copyProperties(serviceDO, serviceDTO);
                list.add(serviceDTO);
            }
        }
        return list;
    }

    @Override
    public int count(ServiceDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        ServiceDO dataobject = new ServiceDO();
        BeanUtils.copyProperties(dto, dataobject);
        int cnt = dao.countByCond(dataobject);
        return cnt;
    }

    @Override
    public ServiceDTO get(long id) {
        Preconditions.checkArgument(id > 0, "id必须大于0");
        ServiceDO dataobject = dao.selectByPrimaryKey(id);
        ServiceDTO serviceDTO = new ServiceDTO();
        BeanUtils.copyProperties(dataobject, serviceDTO);
        return serviceDTO;
    }
}