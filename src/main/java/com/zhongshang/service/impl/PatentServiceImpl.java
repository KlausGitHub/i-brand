package com.zhongshang.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zhongshang.dao.PatentDAO;
import com.zhongshang.dto.PatentDTO;
import com.zhongshang.model.PatentDO;
import com.zhongshang.service.IPatentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PatentServiceImpl implements IPatentService {
    @Resource
    private PatentDAO dao;

    @Override
    public Long create(PatentDTO dto) {
        Preconditions.checkArgument(dto != null, "dto不能为空.");
        PatentDO model = new PatentDO();
        BeanUtils.copyProperties(dto, model);
        dao.insert(model);
        return model.getId();
    }

    @Override
    public int delete(Long id) {
        Preconditions.checkArgument(id > 0, "Id必须大于0");
        int cnt = dao.deleteByPrimaryKey(id);
        return cnt;
    }

    @Override
    public int update(PatentDTO dto) {
        Preconditions.checkArgument(dto != null && dto.getId() != null, "Id不能为空.");
        PatentDO model = new PatentDO();
        BeanUtils.copyProperties(dto, model);
        int cnt = dao.updateByPrimaryKeySelective(model);
        return cnt;
    }

    @Override
    public List<PatentDTO> list(PatentDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        PatentDO model = new PatentDO();
        BeanUtils.copyProperties(dto, model);
        List<PatentDO> models = dao.selectByCond(model);
        List<PatentDTO> lists = Lists.newArrayList();
        if (models != null && models.size() > 0) {
            for (PatentDO pdo : models) {
                PatentDTO bDto = new PatentDTO();
                BeanUtils.copyProperties(pdo, bDto);
                lists.add(bDto);
            }
        }
        return lists;
    }

    @Override
    public int count(PatentDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        PatentDO model = new PatentDO();
        BeanUtils.copyProperties(dto, model);
        int cnt = dao.countByCond(model);
        return cnt;
    }

    @Override
    public PatentDTO get(Long id) {
        Preconditions.checkArgument(id > 0, "id必须大于0");
        PatentDO model = dao.selectByPrimaryKey(id);
        PatentDTO result = new PatentDTO();
        BeanUtils.copyProperties(model, result);
        return result;
    }
}