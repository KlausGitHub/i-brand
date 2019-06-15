package com.zhongshang.web;

import com.alibaba.fastjson.JSONObject;
import com.zhongshang.common.BaseResult;
import com.zhongshang.common.ErrorCode;
import com.zhongshang.common.ResultUtils;
import com.zhongshang.dto.ConfigDTO;
import com.zhongshang.service.IConfigService;
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
@RequestMapping("/v1/config/")
public class ConfigController {

    @Resource
    private IConfigService configService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public BaseResult<ConfigDTO> create(@RequestBody JSONObject paramJson){
        try {
            log.info("创建配置请求开始，参数={}", paramJson.toJSONString());
            ConfigDTO configDTO = new ConfigDTO();
            BeanUtils.copyProperties(configDTO,paramJson);
            configDTO.setIsDeleted("N");
            configDTO.setCreateTime(new Date());
            configDTO.setModifyTime(new Date());
            //CustomerDTO customerDTO = customerService.get(configDTO.getCustomerId());

            long configId = configService.create(configDTO);
            configDTO.setId(configId);
            return ResultUtils.success(configDTO);
        } catch (Exception e) {
            log.error("create error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_CREATE_ERR, null);
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<ConfigDTO> update(@RequestBody JSONObject paramJson){
        try {
            log.info("修改配置请求开始，参数={}", paramJson.toJSONString());
            ConfigDTO configDTO = new ConfigDTO();
            BeanUtils.copyProperties(configDTO,paramJson);
            //CustomerDTO customerDTO = customerService.get(configDTO.getCustomerId());
            configDTO.setModifyTime(new Date());
            int updateNum = configService.update(configDTO);
            if(updateNum == 1){
                return ResultUtils.success(configDTO);
            }else{
                return ResultUtils.fail(ErrorCode.COMMON_REPOSITORY_ERR,null,"未找到数据");
            }

        } catch (Exception e) {
            log.error("update error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_UPDATE_ERR, null);
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult<Boolean> delete(@RequestBody JSONObject paramJson){
        try{
            log.info("删除配置请求开始，参数={}", paramJson.toJSONString());
            ConfigDTO configDTO = new ConfigDTO();
            BeanUtils.copyProperties(configDTO,paramJson);
            int deleteNum = configService.delete(configDTO.getId());
            if(deleteNum > 0){
                return ResultUtils.success(true);
            }else{
                return ResultUtils.fail(ErrorCode.COMMON_REPOSITORY_ERR,null,"未找到数据");
            }
        }catch(Exception e){
            log.error("delete error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_DELETE_ERR, false);
        }
    }

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public BaseResult<ConfigDTO> getById(HttpServletRequest req){
        try{
            log.info("根据ID查询配置请求开始，参数={}", req.getParameter("id"));
            ConfigDTO configDTO = configService.get(Long.parseLong(req.getParameter("id")));
            return ResultUtils.success(configDTO);
        }catch(Exception e){
            log.error("getById brand error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null);
        }
    }

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public BaseResult<List<ConfigDTO>> getList(@RequestBody JSONObject paramJson){
        try{
            log.info("批量查询配置请求开始，参数={}", paramJson.toJSONString());
            ConfigDTO configDTO = new ConfigDTO();
            BeanUtils.copyProperties(configDTO,paramJson);
            List<ConfigDTO> list = configService.list(configDTO);

            return ResultUtils.success(list);
        }catch(Exception e){
            log.error("getList error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null);
        }
    }

}
