package com.zhongshang.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zhongshang.dao.CustomerDAO;
import com.zhongshang.dto.CustomerDTO;
import com.zhongshang.model.CustomerDO;
import com.zhongshang.service.ICustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Resource
    private CustomerDAO dao;

    @Override
    public Long create(CustomerDTO dto) {
        Preconditions.checkArgument(dto != null, "dto不能为空.");
        CustomerDO model = new CustomerDO();
        BeanUtils.copyProperties(dto, model);
        return dao.insert(model);
    }

    @Override
    public int delete(Long id) {
        Preconditions.checkArgument(id > 0, "Id必须大于0");
        int cnt = dao.deleteByPrimaryKey(id);
        return cnt;
    }

    @Override
    public int update(CustomerDTO dto) {
        Preconditions.checkArgument(dto != null && dto.getId() != null, "Id不能为空.");
        CustomerDO model = new CustomerDO();
        BeanUtils.copyProperties(dto, model);
        int cnt = dao.updateByPrimaryKeySelective(model);
        return cnt;
    }

    @Override
    public List<CustomerDTO> list(CustomerDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        CustomerDO model = new CustomerDO();
        BeanUtils.copyProperties(dto, model);
        List<CustomerDO> models = dao.selectByCond(model);
        List<CustomerDTO> lists = Lists.newArrayList();
        if (models != null && models.size() > 0) {
            for (CustomerDO bdo : models) {
                CustomerDTO bDto = new CustomerDTO();
                BeanUtils.copyProperties(bdo, bDto);
                lists.add(bDto);
            }
        }
        return lists;
    }

    @Override
    public int count(CustomerDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        CustomerDO model = new CustomerDO();
        BeanUtils.copyProperties(dto, model);
        int cnt = dao.countByCond(model);
        return cnt;
    }

    @Override
    public CustomerDTO get(Long id) {
        Preconditions.checkArgument(id > 0, "id必须大于0");
        CustomerDO model = dao.selectByPrimaryKey(id);
        CustomerDTO result = new CustomerDTO();
        BeanUtils.copyProperties(model, result);
        return result;
    }
}