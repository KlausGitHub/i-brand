package com.zhongshang.web;

import com.alibaba.fastjson.JSON;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        BaseResult<BrandDTO> result = new BaseResult<BrandDTO>();
        try {
            log.info("创建商标请求开始，参数={}", paramJson.toJSONString());
            BrandDTO brandDTO = new BrandDTO();
            BeanUtils.copyProperties(brandDTO,paramJson);
            brandDTO.setShowFlag("N");
            brandDTO.setDeleteFlag("N");
            CustomerDTO customerDTO = customerService.get(brandDTO.getCustomerId());
            //会员创建的商标为名标
            if(customerDTO != null && customerDTO.getVipFlag().equals("Y")){
                brandDTO.setVipFlag("Y");
            }else{
                brandDTO.setVipFlag("N");
            }

            long brandId = brandService.create(brandDTO);
            brandDTO.setId(brandId);
            result.setResult(brandDTO);
            return ResultUtils.success(brandDTO);
        } catch (Exception e) {
            log.error("create brand error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_CREATE_ERR, null);
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<BrandDTO> update(@RequestBody JSONObject paramJson){
        BaseResult<BrandDTO> result = new BaseResult<BrandDTO>();
        try {
            log.info("修改商标请求开始，参数={}", paramJson.toJSONString());
            BrandDTO brandDTO = new BrandDTO();
            BeanUtils.copyProperties(brandDTO,paramJson);
            CustomerDTO customerDTO = customerService.get(brandDTO.getCustomerId());

            int updateNum = brandService.update(brandDTO);
            result.setResult(brandDTO);
            if(updateNum == 1){
                return ResultUtils.success(brandDTO);
            }else{
                return ResultUtils.fail(ErrorCode.COMMON_REPOSITORY_ERR,null,"未找到数据");
            }

        } catch (Exception e) {
            log.error("update brand error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_UPDATE_ERR, null);
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult<Boolean> delete(@RequestBody JSONObject paramJson){
        BaseResult<Boolean> result = new BaseResult<Boolean>();
        try{
            log.info("删除商标请求开始，参数={}", paramJson.toJSONString());
            BrandDTO brandDTO = new BrandDTO();
            BeanUtils.copyProperties(brandDTO,paramJson);
            brandDTO.setDeleteFlag("Y");
            int deleteNum = brandService.update(brandDTO);
            if(deleteNum > 0){
                return ResultUtils.success(true);
            }else{
                return ResultUtils.fail(ErrorCode.COMMON_REPOSITORY_ERR,null);
            }
        }catch(Exception e){
            log.error("delete brand error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_DELETE_ERR, false);
        }
    }

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public BaseResult<BrandDTO> getById(HttpServletRequest req){
        BaseResult<BrandDTO> result = new BaseResult<BrandDTO>();
        try{
            log.info("根据ID查询商标请求开始，参数={}", req.getParameter("id"));
            BrandDTO brandDTO = brandService.get(Long.parseLong(req.getParameter("id")));
            return ResultUtils.success(brandDTO);
        }catch(Exception e){
            log.error("getById brand error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null);
        }
    }

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public BaseResult<JSONObject> getList(@RequestBody JSONObject paramJson){
        try{
            log.info("批量查询商标请求开始，参数={}", paramJson.toJSONString());
            BrandDTO brandDTO = new BrandDTO();
            BeanUtils.copyProperties(brandDTO,paramJson);
            brandDTO.setDeleteFlag("N");
            Integer pageNum = brandDTO.getPageNum() != 0 ? brandDTO.getPageNum() : 1 ;
            Integer pageSize = brandDTO.getPageSize() != 0 ? brandDTO.getPageSize() : 10 ;
            Page page = PageHelper.startPage(pageNum, pageSize, true);
            List<BrandDTO> list = brandService.list(brandDTO);
            JSONObject json = new JSONObject();
            json.put("pageNum",pageNum);
            json.put("pageSize",pageSize);
            json.put("total",page.getTotal());
            json.put("result",list);
            return ResultUtils.success(json);
        }catch(Exception e){
            log.error("getList brand error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null);
        }
    }

}
