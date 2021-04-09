package com.christli.study.common.operation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@Slf4j
public class FileUpload {

    @PostMapping("uploadFile")
    public List<String> uploadFile(@RequestParam("files") MultipartFile[] files) {

        List<String> list = new ArrayList<>();
        // 判断文件数组长度
        if (files.length <= 0) {
            list.add("请选择文件");
            return list;
        }
        for (MultipartFile file : files) {
            // 源文件名
            String originalFilename = file.getOriginalFilename();
            // 文件格式
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 新文件名，避免文件名重复，造成文件替换问题
            String fileName = UUID.randomUUID() + "." + suffix;
            // 文件存储路径
            String filePath = "/Users/libinbin/data/java/static/";
            // 文件全路径
            File targetFile = new File(filePath + fileName);

            // 判断文件存储目录是否存在，不存在则新建目录
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdir();
            }
            try {
                // 将图片保存
                file.transferTo(targetFile);
                list.add(originalFilename);
            } catch (IOException e) {
                log.info("文件上传异常={}", e);
            }
        }
        return list;
    }
}
