package com.zhongshang.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zhongshang.common.YesOrNoEnum;
import com.zhongshang.dao.ApplyDAO;
import com.zhongshang.dto.ApplyDTO;
import com.zhongshang.model.ApplyDO;
import com.zhongshang.service.IApplyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApplyServiceImpl implements IApplyService {
    @Resource
    private ApplyDAO dao;

    @Override
    public long create(ApplyDTO dto) {
        Preconditions.checkArgument(dto != null, "dto不能为空.");
        ApplyDO dataobject = new ApplyDO();
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
    public int update(ApplyDTO dto) {
        Preconditions.checkArgument(dto != null && dto.getId() != null, "Id不能为空.");
        ApplyDO dataobject = new ApplyDO();
        BeanUtils.copyProperties(dto, dataobject);
        int cnt = dao.updateByPrimaryKeySelective(dataobject);
        return cnt;
    }

    @Override
    public List<ApplyDTO> list(ApplyDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        ApplyDO dataobject = new ApplyDO();
        BeanUtils.copyProperties(dto, dataobject);
        dataobject.setIsDeleted(YesOrNoEnum.NO.getCode());
        List<ApplyDO> dataobjects = dao.selectByCond(dataobject);
        List<ApplyDTO> list = Lists.newArrayList();
        if (dataobjects != null && dataobjects.size() > 0) {
            for (ApplyDO app : dataobjects) {
                ApplyDTO applyDTO = new ApplyDTO();
                BeanUtils.copyProperties(app, applyDTO);
                list.add(applyDTO);
            }
        }
        return list;
    }

    @Override
    public int count(ApplyDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        ApplyDO dataobject = new ApplyDO();
        BeanUtils.copyProperties(dto, dataobject);
        dataobject.setIsDeleted(YesOrNoEnum.NO.getCode());
        int cnt = dao.countByCond(dataobject);
        return cnt;
    }

    @Override
    public ApplyDTO get(long id) {
        Preconditions.checkArgument(id > 0, "id必须大于0");
        ApplyDO dataobject = dao.selectByPrimaryKey(id);
        ApplyDTO applyDTO = new ApplyDTO();
        BeanUtils.copyProperties(dataobject, applyDTO);
        return applyDTO;
    }
}