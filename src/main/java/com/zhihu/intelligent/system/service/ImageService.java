package com.zhihu.intelligent.system.service;

import com.zhihu.intelligent.common.constants.SystemConstants;
import com.zhihu.intelligent.common.utils.UserUtil;
import com.zhihu.intelligent.system.aop.Action;
import com.zhihu.intelligent.system.entity.Image;
import com.zhihu.intelligent.system.exception.GlobalResponse;
import com.zhihu.intelligent.system.exception.ImageUploadFailedException;
import com.zhihu.intelligent.system.repository.ImageRepository;
import com.zhihu.intelligent.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;


@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    private static String imagePath = "image/";


    @Action(type = "CREATE", operation = "上传图片")
    public String save(MultipartFile[] files){
        List<String> filesUrlList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file != null) {
                try {
                    filesUrlList.add(executeUpload(file,SystemConstants.BASE_DIR,imagePath));
                }catch (Exception e){
                    throw new ImageUploadFailedException("图片上传失败");
                }
            }
        }
        GlobalResponse response = new GlobalResponse(200, "图片上传成功");
        response.getData().put("imageUrls", filesUrlList);
        return response.toString();
    }

    private String executeUpload(MultipartFile file,String basePath,String imagePath) throws Exception {
        Image image = new Image();
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String fileName = UUID.randomUUID().toString() + suffix;
        String username = UserUtil.getUsername();
        String userId = userRepository.findByUsername(username).get().getId();
        String path = basePath + imagePath + userId + "/" + fileName;
        //服务端保存的文件对象
        File serverFile = new File(path);
        // 检测是否存在目录
        if (!serverFile.getParentFile().exists()) {
            serverFile.getParentFile().mkdirs();
        }
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);

        // 保存图片信息
        image.setFormat(suffix);
        image.setName(fileName);
        image.setCreateDate(new Date());
        image.setSize(file.getSize());
        image.setImageUrl(SystemConstants.ADDRESS+ imagePath + userId + "/" + fileName);
        image.setUserId(userId);
        imageRepository.save(image);

        return image.getImageUrl();
    }
}
