package com.zhongshang.web;

import com.alibaba.fastjson.JSONObject;
import com.zhongshang.common.BaseResult;
import com.zhongshang.common.ErrorCode;
import com.zhongshang.common.ResultUtils;
import com.zhongshang.dto.ServiceDTO;
import com.zhongshang.service.IServiceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author niuqun
 * @date 2019-06-15
 */
@Slf4j
@RestController
@RequestMapping("/v1/service/")
public class ServiceController {

    @Resource
    private IServiceService serviceService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public BaseResult<ServiceDTO> create(@RequestBody JSONObject paramJson) {
        try {
            log.info("创建服务请求开始，参数={}", paramJson.toJSONString());
            ServiceDTO serviceDTO = new ServiceDTO();
            BeanUtils.copyProperties(serviceDTO, paramJson);
            serviceDTO.setCreateTime(new Date());
            serviceDTO.setModifyTime(new Date());

            long serviceId = serviceService.create(serviceDTO);
            serviceDTO.setId(serviceId);
            return ResultUtils.success(serviceDTO);
        } catch (Exception e) {
            log.error("create error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_CREATE_ERR, null);
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<ServiceDTO> update(@RequestBody JSONObject paramJson) {
        try {
            log.info("修改服务请求开始，参数={}", paramJson.toJSONString());
            ServiceDTO serviceDTO = new ServiceDTO();
            BeanUtils.copyProperties(serviceDTO, paramJson);
            //CustomerDTO customerDTO = customerService.get(serviceDTO.getCustomerId());
            serviceDTO.setModifyTime(new Date());
            int updateNum = serviceService.update(serviceDTO);
            if (updateNum == 1) {
                return ResultUtils.success(serviceDTO);
            } else {
                return ResultUtils.fail(ErrorCode.COMMON_REPOSITORY_ERR, null, "未找到数据");
            }

        } catch (Exception e) {
            log.error("update error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_UPDATE_ERR, null);
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult<Boolean> delete(@RequestBody JSONObject paramJson) {
        try {
            log.info("删除服务请求开始，参数={}", paramJson.toJSONString());
            ServiceDTO serviceDTO = new ServiceDTO();
            BeanUtils.copyProperties(serviceDTO, paramJson);
            int deleteNum = serviceService.delete(serviceDTO.getId());
            if (deleteNum > 0) {
                return ResultUtils.success(true);
            } else {
                return ResultUtils.fail(ErrorCode.COMMON_REPOSITORY_ERR, null, "未找到数据");
            }
        } catch (Exception e) {
            log.error("delete error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_DELETE_ERR, false);
        }
    }

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public BaseResult<ServiceDTO> getById(HttpServletRequest req) {
        try {
            log.info("根据ID查询服务请求开始，参数={}", req.getParameter("id"));
            ServiceDTO serviceDTO = serviceService.get(Long.parseLong(req.getParameter("id")));
            return ResultUtils.success(serviceDTO);
        } catch (Exception e) {
            log.error("getById brand error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null);
        }
    }

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public BaseResult<List<ServiceDTO>> getList(@RequestBody JSONObject paramJson) {
        try {
            log.info("批量查询服务请求开始，参数={}", paramJson.toJSONString());
            ServiceDTO serviceDTO = new ServiceDTO();
            BeanUtils.copyProperties(serviceDTO, paramJson);
            List<ServiceDTO> list = serviceService.list(serviceDTO);

            return ResultUtils.success(list);
        } catch (Exception e) {
            log.error("getList error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null);
        }
    }

}