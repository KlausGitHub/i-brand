package com.zhongshang.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zhongshang.common.*;
import com.zhongshang.dto.ApplyDTO;
import com.zhongshang.dto.BrandDTO;
import com.zhongshang.dto.PatentDTO;
import com.zhongshang.request.ApplyRequest;
import com.zhongshang.service.IApplyService;
import com.zhongshang.service.IBrandService;
import com.zhongshang.service.ICustomerService;
import com.zhongshang.service.IPatentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yangsheng
 * @date 2019-06-15
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/apply/")
public class ApplyController {

    @Resource
    private IApplyService applyService;
    @Resource
    private IBrandService brandService;
    @Resource
    private IPatentService patentService;
    @Resource
    private ICustomerService customerService;

    /**
     * 申请服务
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "doApply", method = RequestMethod.POST)
    public BaseResult<Boolean> apply(@RequestBody ApplyRequest request) {
        log.info("申请购买商标、专利、服务、会员请求开始,请求参数={}", JSON.toJSONString(request));
        try {
            ApplyDTO appDto = new ApplyDTO();
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(appDto, request);
            appDto.setIsDeleted(YesOrNoEnum.NO.getCode());
            appDto.setCreateTime(new Date());
            appDto.setModifyTime(new Date());
            appDto.setStatus(ApplyStatusEnum.APPLY.getCode());
            //appDto.setScore(0);
            Long id = applyService.create(appDto);
            return ResultUtils.success(id > 0);
        } catch (Exception e) {
            log.error("申请购买商标、专利、服务、会员失败", e);
            return ResultUtils.fail(ErrorCode.APPLY_ERROR, false);
        }
    }

    /**
     * 更新申请
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<ApplyDTO> update(@RequestBody JSONObject req) {
        log.info("修改申请信息请求开始,请求参数={}", JSON.toJSONString(req));
        try {
            ApplyDTO appDto = new ApplyDTO();
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(appDto, req);
            applyService.update(appDto);
            return ResultUtils.success(applyService.get(appDto.getId()));
        } catch (Exception e) {
            log.error("更新申请信息失败", e);
            return ResultUtils.fail(ErrorCode.UPDATE_CUSTOMER_ERROR, null);
        }
    }

    /**
     * 删除申请
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult<Boolean> delete(@RequestBody JSONObject req) {
        log.info("删除申请信息请求开始,请求参数={}", JSON.toJSONString(req));
        try {
            ApplyDTO appDto = new ApplyDTO();
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(appDto, req);
            appDto.setIsDeleted(YesOrNoEnum.YES.getCode());
            int cnt = applyService.update(appDto);
            return ResultUtils.success(cnt > 0);
        } catch (Exception e) {
            log.error("删除申请信息失败", e);
            return ResultUtils.fail(ErrorCode.UPDATE_CUSTOMER_ERROR, null);
        }
    }

    /**
     * 查询服务申请列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public BaseResult<JSONObject> list(@RequestBody JSONObject req) {
        log.info("查询申请服务列表请求开始,请求参数={}", JSON.toJSONString(req));
        JSONObject json = new JSONObject();
        try {
            ApplyDTO appDto = new ApplyDTO();
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(appDto, req);
            appDto.setIsDeleted("N");
            Integer pageNum = req.getInteger("pageNum");
            Integer pageSize = req.getInteger("pageSize");
            Page page = PageHelper.startPage((pageNum != null && pageNum > 0) ? pageNum : 1,
                    (pageSize != null && pageSize > 0) ? pageSize : 10, true);
            List<ApplyDTO> list = applyService.list(appDto);
            if (list == null || list.size() <= 0) {
                list = Lists.newArrayList();
            }
            //商标列表
            if (appDto.getApplyType() == 1) {
                List<Long> targetIds = list.stream().map(ApplyDTO::getTargetId).collect(Collectors.toList());
                List<BrandDTO> brands = brandService.listByIds(targetIds);
                if (CollectionUtils.isNotEmpty(brands)) {
                    Map<Long, BrandDTO> brandDTOMap = brands.stream().collect(Collectors.toMap(BrandDTO::getId, o -> o));
                    for (int i = 0; i < list.size(); i++) {
                        ApplyDTO apply = list.get(i);
                        BrandDTO ad = brandDTOMap.get(apply.getTargetId());
                        apply.setTargetJson(JSON.parseObject(JSON.toJSONString(ad)));
                    }
                }
            } else if (appDto.getApplyType() == 2) {
                //专利
                List<Long> targetIds = list.stream().map(ApplyDTO::getTargetId).collect(Collectors.toList());
                List<PatentDTO> patents = patentService.listByIds(targetIds);
                if (CollectionUtils.isNotEmpty(patents)){
                    Map<Long, PatentDTO> patentDTOMap = patents.stream().collect(Collectors.toMap(PatentDTO::getId, o -> o));
                    for (int i = 0; i < list.size(); i++) {
                        ApplyDTO apply = list.get(i);
                        PatentDTO pd = patentDTOMap.get(apply.getTargetId());
                        apply.setTargetJson(JSON.parseObject(JSON.toJSONString(pd)));
                    }
                }
            }
            json.put("pageNum", pageNum);
            json.put("pageSize", pageSize);
            json.put("total", page.getTotal());
            json.put("result", list);
            return ResultUtils.success(json);
        } catch (Exception e) {
            log.error("查询服务申请列表失败", e);
            return ResultUtils.fail(ErrorCode.SYS_RUN_EXCEPTION, json, "服务申请");
        }
    }

    /**
     * 查询服务申请详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    private BaseResult<ApplyDTO> queryById(@PathVariable("id") Long id) {
        log.info("查询服务申请详情信息,id={}", id);
        try {
            Preconditions.checkNotNull(id);
            ApplyDTO apply = applyService.get(id);
            if (apply == null) {
                return ResultUtils.fail(ErrorCode.APPLY_NOT_EXIST, apply);
            }
            //CustomerDTO customer = customerService.get(apply.getCustomerId());
            //json.put("apply", apply);
            //json.put("customer", customer);
            return ResultUtils.success(apply);
        } catch (Exception e) {
            log.error("查询服务申请详情失败", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null, "服务申请");
        }
    }
}
