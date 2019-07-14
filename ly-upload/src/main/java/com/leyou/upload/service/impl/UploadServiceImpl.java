package com.leyou.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExcetion;
import com.leyou.upload.config.UploadProperties;
import com.leyou.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadServiceImpl implements UploadService {

/*    // 支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");*/

    @Autowired
    private UploadProperties properties;

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 1. 校验文件大小
     * 2. 校验文件的媒体类型
     * 3. 校验文件的内容
     *
     * @param file 图片
     * @return 返回图片fastDFS网络路径
     */
    @Override
    public String uploadImage(MultipartFile file) {
        try {
            //校验文件类型
            String contentType = file.getContentType();
            if (!properties.getAllowTypes().contains(contentType)) {
                log.info("上传失败，文件类型不匹配：{}", contentType);
                throw new LyExcetion(ExceptionEnum.INVALID_FILE_TYPE);
            }
            //校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                log.info("上传失败，文件内容不符合要求");
                throw new LyExcetion(ExceptionEnum.UPLOAD_FILE_ERROR);
            }
            // 2、保存图片
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            return properties.getBaseUrl()+storePath.getFullPath();
        } catch (IOException e) {
            throw new LyExcetion(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }
}
