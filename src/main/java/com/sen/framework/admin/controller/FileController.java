package com.sen.framework.admin.controller;


import com.sen.framework.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Evan Huang
 * @since 2019-03-14
 */
@Slf4j
@RestController
@RequestMapping("/common/file")
public class FileController {
    @PostMapping("/upload")
    public String upload(@RequestBody MultipartFile file) {
        if (file.isEmpty()) {
            throw new SystemException("请选择文件");
        }
        try {
            String filename = file.getOriginalFilename();
            File dir = new File(System.getProperty("user.dir") + "/static/");
            if (!dir.exists()) {
                boolean mkdirsSuccess = dir.mkdirs();
                if (!mkdirsSuccess) {
                    throw new SystemException("创建静态资源目录失败");
                }
            }
            File path = new File(System.getProperty("user.dir") + "/static/" + filename);
            file.transferTo(path);
            return path.getAbsolutePath();
        } catch (FileNotFoundException e) {
            throw new SystemException("文件上传失败，未找到文件路径", e);
        } catch (IOException e) {
            throw new SystemException("文件上传失败，转换失败", e);
        }
    }
}
