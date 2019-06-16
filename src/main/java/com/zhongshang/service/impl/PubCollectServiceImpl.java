package com.zhongshang.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zhongshang.common.YesOrNoEnum;
import com.zhongshang.dao.PubCollectDAO;
import com.zhongshang.dto.PubCollectDTO;
import com.zhongshang.model.PubCollectDO;
import com.zhongshang.service.IPubCollectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PubCollectServiceImpl implements IPubCollectService {
    @Resource
    private PubCollectDAO dao;

    @Override
    public Long create(PubCollectDTO dto) {
        Preconditions.checkArgument(dto != null, "dto不能为空.");
        PubCollectDO dataobject = new PubCollectDO();
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
    public int update(PubCollectDTO dto) {
        Preconditions.checkArgument(dto != null && dto.getId() != null, "Id不能为空.");
        PubCollectDO dataobject = new PubCollectDO();
        BeanUtils.copyProperties(dto, dataobject);
        int cnt = dao.updateByPrimaryKeySelective(dataobject);
        return cnt;
    }

    @Override
    public List<PubCollectDTO> list(PubCollectDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        PubCollectDO dataobject = new PubCollectDO();
        BeanUtils.copyProperties(dto, dataobject);
        dataobject.setIsDeleted(YesOrNoEnum.NO.getCode());
        List<PubCollectDO> dataobjects = dao.selectByCond(dataobject);
        List<PubCollectDTO> lists = Lists.newArrayList();
        if (dataobjects != null && dataobjects.size() > 0) {
            for (PubCollectDO pub : dataobjects) {
                PubCollectDTO pubCollectDTO = new PubCollectDTO();
                BeanUtils.copyProperties(pub, pubCollectDTO);
                lists.add(pubCollectDTO);
            }
        }
        return lists;
    }

    @Override
    public int count(PubCollectDTO dto) {
        Preconditions.checkArgument(dto != null, "查询条件不能为空.");
        PubCollectDO dataobject = new PubCollectDO();
        BeanUtils.copyProperties(dto, dataobject);
        dataobject.setIsDeleted(YesOrNoEnum.NO.getCode());
        int cnt = dao.countByCond(dataobject);
        return cnt;
    }

    @Override
    public PubCollectDTO get(long id) {
        Preconditions.checkArgument(id > 0, "id必须大于0");
        PubCollectDO dataobject = dao.selectByPrimaryKey(id);
        PubCollectDTO pubCollectDTO = new PubCollectDTO();
        BeanUtils.copyProperties(dataobject, pubCollectDTO);
        return pubCollectDTO;
    }
}