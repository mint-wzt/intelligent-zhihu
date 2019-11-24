package com.zhihu.intelligent.system.controller;

import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.service.ImageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @ApiOperation(value = "上传图片", notes = "上传图片")
    @PostMapping("/images")
    @PreAuthorize("hasRole('CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public GlobalResponse uploadIamge(@RequestParam("id") String id, @RequestParam("files") MultipartFile[] files) {
        return imageService.save(files, id);
    }

}
