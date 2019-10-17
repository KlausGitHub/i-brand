package com.zhongshang.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhongshang.common.*;
import com.zhongshang.dto.BrandDTO;
import com.zhongshang.dto.PatentDTO;
import com.zhongshang.dto.PubCollectDTO;
import com.zhongshang.service.IBrandService;
import com.zhongshang.service.IPatentService;
import com.zhongshang.service.IPubCollectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangsheng
 * @date 2019-06-16
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/pubOrCollect/")
public class PubCollectController {

    @Resource
    private IPubCollectService pubCollectService;
    @Resource
    private IBrandService brandService;
    @Resource
    private IPatentService patentService;

    /**
     * 发布或者收藏(商标、专利)
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public BaseResult<Boolean> pubOrCollect(@RequestBody JSONObject req) {
        try {
            log.info("发布或者收藏商标、专利请求开始,请求参数={}", JSON.toJSONString(req));
            PubCollectDTO pubDto = new PubCollectDTO();
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(pubDto, req);
            pubDto.setCreateTime(new Date());
            pubDto.setModifyTime(new Date());
            pubDto.setIsDeleted("N");
            Long id = pubCollectService.create(pubDto);
            return ResultUtils.success(id > 0);
        } catch (Exception e) {
            log.error("发布或者收藏商标、专利失败", e);
            return ResultUtils.fail(ErrorCode.PUB_COLLECT_ERROR, false);
        }
    }

    /**
     * 更新发布信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseResult<Boolean> update(@RequestBody JSONObject req) {
        try {
            log.info("更新商标、专利的发布请求开始,请求参数={}", JSON.toJSONString(req));
            PubCollectDTO pubDto = new PubCollectDTO();
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(pubDto, req);
            pubDto.setModifyTime(new Date());
            int cnt = pubCollectService.update(pubDto);
            return ResultUtils.success(cnt > 0);
        } catch (Exception e) {
            log.error("更新发布或者收藏商标、专利失败", e);
            return ResultUtils.fail(ErrorCode.PUB_COLLECT_ERROR, false);
        }
    }


    /**
     * 删除发布或者收藏(商标、专利)
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult<Boolean> delete(@RequestBody JSONObject req) {
        try {
            log.info("删除商标、专利的发布请求开始,请求参数={}", JSON.toJSONString(req));
            PubCollectDTO pubDto = new PubCollectDTO();
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(pubDto, req);
            pubDto.setModifyTime(new Date());
            pubDto.setIsDeleted("Y");
            int cnt = pubCollectService.update(pubDto);
            return ResultUtils.success(cnt > 0);
        } catch (Exception e) {
            log.error("删除发布或者收藏商标、专利失败", e);
            return ResultUtils.fail(ErrorCode.PUB_COLLECT_ERROR, false);
        }
    }

    /**
     * 根据主键查询发布的 商标、专利信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public BaseResult<PubCollectDTO> getById(@RequestBody JSONObject req) {
        try {
            log.info("查询商标、专利的发布详情请求开始,请求参数={}", JSON.toJSONString(req));
            Long id = req.getLong("id");

            PubCollectDTO pubCollectDTO = pubCollectService.get(id);
            return ResultUtils.success(pubCollectDTO);
        } catch (Exception e) {
            log.error("查询发布或者收藏商标、专利详情失败", e);
            return ResultUtils.fail(ErrorCode.PUB_COLLECT_ERROR, null);
        }
    }


    /**
     * 发布、收藏列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public BaseResult<JSONObject> list(@RequestBody JSONObject req) {
        JSONObject json = new JSONObject();
        try {
            log.info("查询收藏的商标、专利列表请求开始,请求参数={}", JSON.toJSONString(req));
            PubCollectDTO pubDto = new PubCollectDTO();
            pubDto.setIsDeleted("N");
            ConvertUtils.register(new DateLocaleConverter(), Date.class);
            BeanUtils.copyProperties(pubDto, req);
            Integer pageNum = req.getInteger("pageNum");
            Integer pageSize = req.getInteger("pageSize");
            Page page = PageHelper.startPage((pageNum != null && pageNum > 0) ? pageNum : 1,
                    (pageSize != null && pageSize > 0) ? pageSize : 10, true);
            List<PubCollectDTO> pubList = pubCollectService.list(pubDto);
            if (pubDto.getActionType() == BrandConstant.COLLECT_TYPE) {
                JSONObject targetJson = new JSONObject();
                //获取自己收藏的商标、专利列表
                List<Long> targetIds = pubList.stream().map(PubCollectDTO::getTargetId).collect(Collectors.toList());
                if (pubDto.getContentType().equals(TypeEnum.BRAND.getCode())) {
                    //调用brand的查询
                    List<BrandDTO> brands = brandService.listByIds(targetIds);
                    targetJson.put("brands", brands);
                } else if (pubDto.getContentType().equals(TypeEnum.PATENT.getCode())) {
                    //调用patent的查询
                    List<PatentDTO> patents = patentService.listByIds(targetIds);
                    targetJson.put("patents", patents);
                }
                json.put("result", targetJson);
            } else {
                json.put("result", pubList);
            }

            json.put("pageNum", pageNum);
            json.put("pageSize", pageSize);
            json.put("total", page.getTotal());
            return ResultUtils.success(json);
        } catch (Exception e) {
            log.error("查询收藏的商标、专利列表失败", e);
            return ResultUtils.fail(ErrorCode.PUB_COLLECT_LIST_ERROR, json);
        }
    }
}
