package com.zhongshang.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zhongshang.dao.BrandDAO;
import com.zhongshang.dto.BrandDTO;
import com.zhongshang.model.BrandDO;
import com.zhongshang.service.IBrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService {

    @Resource
    private BrandDAO dao;

    @Override
    public Long create(BrandDTO dto) {
        Preconditions.checkArgument(dto != null, "dto不能为空.");
        BrandDO model = new BrandDO();
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
    public int update(BrandDTO dto) {
        Preconditions.checkArgument(dto != null && dto.getId() != null, "Id不能为空.");
        BrandDO model = new BrandDO();
        BeanUtils.copyProperties(dto, model);
        int cnt = dao.updateByPrimaryKeySelective(model);
        return cnt;
    }

    @Override
    public List<BrandDTO> list(BrandDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        BrandDO model = new BrandDO();
        BeanUtils.copyProperties(dto, model);
        List<BrandDO> models = dao.selectByCond(model);
        List<BrandDTO> lists = Lists.newArrayList();
        if (models != null && models.size() > 0) {
            for (BrandDO bdo : models) {
                BrandDTO bDto = new BrandDTO();
                BeanUtils.copyProperties(bdo, bDto);
                lists.add(bDto);
            }
        }
        return lists;
    }

    @Override
    public int count(BrandDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        BrandDO model = new BrandDO();
        BeanUtils.copyProperties(dto, model);
        int cnt = dao.countByCond(model);
        return cnt;
    }

    @Override
    public BrandDTO get(Long id) {
        Preconditions.checkArgument(id > 0, "id必须大于0");
        BrandDO model = dao.selectByPrimaryKey(id);
        BrandDTO result = new BrandDTO();
        BeanUtils.copyProperties(model, result);
        return result;
    }
}