package com.gnw.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class UploadFileImagesUtil {
    /*上传文件到指定路径*/
    public Map<String,Object> uploadFile(String absoluteFilePath, MultipartFile file) throws IOException {
        Map<String,Object> map = new HashMap<>();
        if(file.isEmpty()){
            map.put("Error", "file empty");
            return map;
        }
        String orgFileName;
        String fileName;
        orgFileName = file.getOriginalFilename();
        fileName = UUID.randomUUID().toString()+orgFileName;
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        //long fileSize = file.getSize();//文件大小
        File uploadFilePath = new File(absoluteFilePath);
        if(!uploadFilePath.exists()){
            uploadFilePath.mkdir();
        }
        File newFilePath = new File(absoluteFilePath+"/"+fileName);
        file.transferTo(newFilePath);
        String uploadFileUrl = newFilePath.toString();
        map.put("uploadFileUrl", uploadFileUrl);
        return map;
    }
    /*上传图片到指定路径*/
    public Map<String,Object> uploadImages(String absolutePath,MultipartFile[] images){
        Map<String,Object> map = new HashMap<>();
        if(images.length<=0){
            map.put("Error", "images empty");
            return map;
        }
        String imagesPath;
        for(int i=0;i<images.length;i++){
            String orgFileName = images[i].getOriginalFilename();
            String newFileName = UUID.randomUUID().toString()+orgFileName.substring(orgFileName.lastIndexOf("."));
            File dest = new File(absolutePath+newFileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            String singlePathUrl = absolutePath+newFileName;
        }




        return map;
    }
}
