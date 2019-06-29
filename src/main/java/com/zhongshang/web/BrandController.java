package com.zhongshang.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhongshang.common.BaseResult;
import com.zhongshang.common.ErrorCode;
import com.zhongshang.common.ResultUtils;
import com.zhongshang.dto.BrandDTO;
import com.zhongshang.dto.CustomerDTO;
import com.zhongshang.service.IBrandService;
import com.zhongshang.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author niuqun
 * @date 2019-05-18
 */
@Slf4j
@RestController
@RequestMapping("/v1/brand/")
public class BrandController {

    @Resource
    private IBrandService brandService;
    @Resource
    private ICustomerService customerService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public BaseResult<BrandDTO> create(@RequestBody JSONObject paramJson){
        try {
            log.info("创建商标请求开始，参数={}", paramJson.toJSONString());
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BrandDTO brandDTO = new BrandDTO();
            BeanUtils.copyProperties(brandDTO,paramJson);
            brandDTO.setShowFlag("N");
            brandDTO.setDeleteFlag("N");
            brandDTO.setCreateTime(new Date());
            brandDTO.setModifyTime(new Date());
            CustomerDTO customerDTO = customerService.get(brandDTO.getCustomerId());
            //会员创建的商标为名标
            if(customerDTO != null && customerDTO.getVipFlag().equals("Y")){
                brandDTO.setVipFlag("Y");
            }else{
                brandDTO.setVipFlag("N");
            }
            long brandId = brandService.create(brandDTO);
            brandDTO.setId(brandId);
            return ResultUtils.success(brandDTO);
        } catch (Exception e) {
            log.error("create error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_CREATE_ERR, null,"系统异常!");
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<BrandDTO> update(@RequestBody JSONObject paramJson){
        try {
            log.info("修改商标请求开始，参数={}", paramJson.toJSONString());
            BrandDTO brandDTO = new BrandDTO();
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(brandDTO,paramJson);
            CustomerDTO customerDTO = customerService.get(brandDTO.getCustomerId());
            brandDTO.setModifyTime(new Date());
            int updateNum = brandService.update(brandDTO);
            if(updateNum == 1){
                return ResultUtils.success(brandDTO);
            }else{
                return ResultUtils.fail(ErrorCode.COMMON_REPOSITORY_ERR,null,"未找到数据");
            }

        } catch (Exception e) {
            log.error("update error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_UPDATE_ERR, null,"系统异常!");
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult<Boolean> delete(@RequestBody JSONObject paramJson){
        try{
            log.info("删除商标请求开始，参数={}", paramJson.toJSONString());
            String delteIds = paramJson.getString("ids");
            String failMessage = "";
            if(StringUtils.isNotBlank(delteIds) && delteIds.split(",").length > 0){
                for (String id : delteIds.split(",")){
                    BrandDTO brandDTO = new BrandDTO();
                    brandDTO.setId(Long.parseLong(id));
                    brandDTO.setDeleteFlag("Y");
                    brandDTO.setModifyTime(new Date());
                    int deleteNum = brandService.update(brandDTO);
                    if(deleteNum <= 0){
                        failMessage+="["+id+"]删除失败!";
                    }
                }
            }
            if(StringUtils.isNotBlank(failMessage)){
                return ResultUtils.fail(ErrorCode.COMMON_REPOSITORY_ERR,null,failMessage);
            }
            return ResultUtils.success(true);
        }catch(Exception e){
            log.error("delete error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_DELETE_ERR, false,"系统异常!");
        }
    }

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public BaseResult<BrandDTO> getById(HttpServletRequest req){
        try{
            log.info("根据ID查询商标请求开始，参数={}", req.getParameter("id"));
            BrandDTO brandDTO = brandService.get(Long.parseLong(req.getParameter("id")));
            if(brandDTO != null && brandDTO.getCustomerId() != null){
                CustomerDTO customer = customerService.get(brandDTO.getCustomerId());
                if(customer != null){
                    brandDTO.setCustomerName(customer.getName());
                    brandDTO.setCustomerLoginName(customer.getLoginName());
                    brandDTO.setCustomerMobile(customer.getMobile());
                }

            }
            return ResultUtils.success(brandDTO);
        }catch(Exception e){
            log.error("getById error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null,"系统异常!");
        }
    }

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public BaseResult<JSONObject> getList(@RequestBody JSONObject paramJson){
        try{
            log.info("批量查询商标请求开始，参数={}", paramJson.toJSONString());
            BrandDTO brandDTO = new BrandDTO();
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(brandDTO,paramJson);
            brandDTO.setDeleteFlag("N");

            if(brandDTO != null && StringUtils.isNotBlank(brandDTO.getCustomerMobile())){
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setMobile(brandDTO.getCustomerMobile());
                List<CustomerDTO> searCustomerList = customerService.list(customerDTO);
                if(searCustomerList != null && searCustomerList.size()>0){
                    if(searCustomerList.get(0) != null){
                        brandDTO.setCustomerId(searCustomerList.get(0).getId());
                    }
                }
            }

            Integer pageNum = brandDTO.getPageNum() != 0 ? brandDTO.getPageNum() : 1 ;
            Integer pageSize = brandDTO.getPageSize() != 0 ? brandDTO.getPageSize() : 10 ;
            Page page = PageHelper.startPage(pageNum, pageSize, true);
            List<BrandDTO> list = brandService.list(brandDTO);

            if(list != null && list.size() > 0 ){
                List<Long> ids = list.stream().map(BrandDTO::getCustomerId).collect(Collectors.toList());
                if(ids != null && ids.size() > 0){
                    List<CustomerDTO> customerDTOList = customerService.listByIds(ids);
                    if(customerDTOList != null && customerDTOList.size() > 0){
                        Map<Long,CustomerDTO> customerDTOMap = customerDTOList.stream().collect(Collectors.toMap(CustomerDTO::getId,o->o));
                        for(BrandDTO brand  : list){
                            CustomerDTO customerDTO = customerDTOMap.get(brand.getCustomerId());
                            if(customerDTO == null)
                                continue;
                            brand.setCustomerName(customerDTO.getName());
                            brand.setCustomerLoginName(customerDTO.getLoginName());
                            brand.setCustomerMobile(customerDTO.getMobile());
                        }
                    }
                }
            }
            JSONObject json = new JSONObject();
            json.put("pageNum",pageNum);
            json.put("pageSize",pageSize);
            json.put("total",page.getTotal());
            json.put("result",list);
            return ResultUtils.success(json);
        }catch(Exception e){
            log.error("getList error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null,"系统异常!");
        }
    }

}
