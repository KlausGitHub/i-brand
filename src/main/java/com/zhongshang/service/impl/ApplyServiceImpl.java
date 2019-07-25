package com.zhongshang.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zhongshang.common.YesOrNoEnum;
import com.zhongshang.dao.ApplyDAO;
import com.zhongshang.dto.ApplyDTO;
import com.zhongshang.dto.BrandDTO;
import com.zhongshang.dto.CustomerDTO;
import com.zhongshang.dto.PatentDTO;
import com.zhongshang.model.ApplyDO;
import com.zhongshang.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApplyServiceImpl implements IApplyService {
    @Resource
    private ApplyDAO dao;

    @Resource
    private ICustomerService customerService;
    @Resource
    private IBrandService brandService;
    @Resource
    private IPatentService patentService;
    @Resource
    private IServiceService serviceService;

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
                CustomerDTO buyerCustomerDTO = customerService.get(applyDTO.getCustomerId());
                applyDTO.setBuyerMobile(buyerCustomerDTO != null ? buyerCustomerDTO.getMobile() : null);
                if (applyDTO.getApplyType() == null) {
                    throw new NullPointerException("订单数据异常，类型为空");
                }
                //1-商标2-专利3-服务4-会员
                if (applyDTO.getApplyType() == 1 && applyDTO.getTargetId() != null) {
                    BrandDTO brandDTO = brandService.get(applyDTO.getTargetId());
                    CustomerDTO brandCusDTO = customerService.get(brandDTO.getCustomerId());
                    applyDTO.setSallerMobile(brandCusDTO != null ? brandCusDTO.getMobile() : null);
                } else if (applyDTO.getApplyType() == 2 && applyDTO.getTargetId() != null) {
                    PatentDTO patentDTO = patentService.get(applyDTO.getTargetId());
                    CustomerDTO patentCusDTO = customerService.get(patentDTO.getCustomerId());
                    applyDTO.setSallerMobile(patentCusDTO != null ? patentCusDTO.getMobile() : null);
                }
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
        if (dataobject == null) {
            return null;
        }
        ApplyDTO applyDTO = new ApplyDTO();
        BeanUtils.copyProperties(dataobject, applyDTO);

        CustomerDTO buyerCustomerDTO = customerService.get(applyDTO.getCustomerId());
        applyDTO.setBuyerMobile(buyerCustomerDTO != null ? buyerCustomerDTO.getMobile() : null);
        if (applyDTO.getApplyType() == null) {
            throw new NullPointerException("订单数据异常，类型为空");
        }
        //1-商标2-专利3-服务4-会员
        if (applyDTO.getApplyType() == 1 && applyDTO.getTargetId() != null) {
            BrandDTO brandDTO = brandService.get(applyDTO.getTargetId());
            CustomerDTO brandCusDTO = customerService.get(brandDTO.getCustomerId());
            applyDTO.setSallerMobile(brandCusDTO != null ? brandCusDTO.getMobile() : null);
        } else if (applyDTO.getApplyType() == 2 && applyDTO.getTargetId() != null) {
            PatentDTO patentDTO = patentService.get(applyDTO.getTargetId());
            CustomerDTO patentCusDTO = customerService.get(patentDTO.getCustomerId());
            applyDTO.setSallerMobile(patentCusDTO != null ? patentCusDTO.getMobile() : null);
        }
        return applyDTO;
    }
}