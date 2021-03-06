package com.zhongshang.web;


import com.zhongshang.common.BaseResult;
import com.zhongshang.common.ErrorCode;
import com.zhongshang.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author niuqun
 * @date 2019-05-19
 */
@Slf4j
@RestController
@RequestMapping("/v1/upload/")
public class UploadController {

    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${uploadPath}")
    private String uploadPath;

    @PostMapping("image")
    public BaseResult<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultUtils.fail(ErrorCode.COMMON_NOT_EMPTY_ERR, "上传图片失败，文件信息");
        }
        String fileName = file.getOriginalFilename();
        if (!fileName.substring(fileName.lastIndexOf(".")).equals(".jpg") && !fileName.substring(fileName.lastIndexOf(".")).equals(".png")) {
            return ResultUtils.fail(ErrorCode.COMMON_NOT_EMPTY_ERR, "上传图片失败，只支持jpg、png格式");
        }
        File imageFile = new File(uploadPath);
        if (!imageFile.exists()) {
            imageFile.mkdirs();
        }
        String name = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        imageFile = new File(uploadPath + name);
        if (imageFile.exists()) {
            return ResultUtils.fail(ErrorCode.COMMON_UPLOAD_ERR, "该文件已存在，请重试！");
        }
        try {
            file.transferTo(imageFile);
            log.info("上传成功");
            String path = "/img/" + name;
            return ResultUtils.success(path);
        } catch (IOException e) {
            log.error("file upload failed,caused by = {}", e);
            return ResultUtils.fail(ErrorCode.COMMON_UPLOAD_ERR, null);
        }
    }

}
