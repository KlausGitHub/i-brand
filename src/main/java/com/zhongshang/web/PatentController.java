package com.zhongshang.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhongshang.common.BaseResult;
import com.zhongshang.common.ErrorCode;
import com.zhongshang.common.ResultUtils;
import com.zhongshang.dto.CustomerDTO;
import com.zhongshang.dto.PatentDTO;
import com.zhongshang.service.ICustomerService;
import com.zhongshang.service.IPatentService;
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
 * @date 2019-05-19
 */
@Slf4j
@RestController
@RequestMapping("/v1/patent/")
public class PatentController {

    @Resource
    private IPatentService patentService;
    @Resource
    private ICustomerService customerService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public BaseResult<PatentDTO> create(@RequestBody JSONObject paramJson){
        try {
            log.info("创建专利请求开始，参数={}", paramJson.toJSONString());
            PatentDTO patentDTO = new PatentDTO();
            BeanUtils.copyProperties(patentDTO,paramJson);
            patentDTO.setShowFlag("N");
            patentDTO.setDeleteFlag("N");
            //CustomerDTO customerDTO = customerService.get(patentDTO.getCustomerId());

            long patnetId = patentService.create(patentDTO);
            patentDTO.setId(patnetId);
            return ResultUtils.success(patentDTO);
        } catch (Exception e) {
            log.error("create error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_CREATE_ERR, null);
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<PatentDTO> update(@RequestBody JSONObject paramJson){
        try {
            log.info("修改专利请求开始，参数={}", paramJson.toJSONString());
            PatentDTO patentDTO = new PatentDTO();
            BeanUtils.copyProperties(patentDTO,paramJson);
            //CustomerDTO customerDTO = customerService.get(patentDTO.getCustomerId());

            int updateNum = patentService.update(patentDTO);
            if(updateNum == 1){
                return ResultUtils.success(patentDTO);
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
            log.info("删除专利请求开始，参数={}", paramJson.toJSONString());
            PatentDTO patentDTO = new PatentDTO();
            BeanUtils.copyProperties(patentDTO,paramJson);
            patentDTO.setDeleteFlag("Y");
            int deleteNum = patentService.update(patentDTO);
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
    public BaseResult<PatentDTO> getById(HttpServletRequest req){
        try{
            log.info("根据ID查询专利请求开始，参数={}", req.getParameter("id"));
            PatentDTO patentDTO = patentService.get(Long.parseLong(req.getParameter("id")));
            return ResultUtils.success(patentDTO);
        }catch(Exception e){
            log.error("getById brand error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null);
        }
    }

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public BaseResult<JSONObject> getList(@RequestBody JSONObject paramJson){
        try{
            log.info("批量查询专利请求开始，参数={}", paramJson.toJSONString());
            PatentDTO patentDTO = new PatentDTO();
            BeanUtils.copyProperties(patentDTO,paramJson);
            patentDTO.setDeleteFlag("N");
            Integer pageNum = patentDTO.getPageNum() != 0 ? patentDTO.getPageNum() : 1 ;
            Integer pageSize = patentDTO.getPageSize() != 0 ? patentDTO.getPageSize() : 10 ;
            Page page = PageHelper.startPage(pageNum, pageSize, true);
            List<PatentDTO> list = patentService.list(patentDTO);
            JSONObject json = new JSONObject();
            json.put("pageNum",pageNum);
            json.put("pageSize",pageSize);
            json.put("total",page.getTotal());
            json.put("result",list);
            return ResultUtils.success(json);
        }catch(Exception e){
            log.error("getList error, caused by ={}", e);
            return ResultUtils.fail(ErrorCode.COMMON_QUERY_ERR, null);
        }
    }

}
