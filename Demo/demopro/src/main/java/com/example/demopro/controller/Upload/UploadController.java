package com.example.demopro.controller.Upload;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UploadController {
    //本机的文件路径
    String pathname = "/Users/wanna/Desktop/blog/UserLoginDemo/Demo/demopro/src/main/resources/file/logo";
    //服务器的文件路径
    //String pathname = "/java/file/logo";

    @RequestMapping(value = "/file/getlogo", method = RequestMethod.GET)
    public void GetLogo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取用户输入的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String filename = "logo_" + username + ".jpg";  //根据用户名生成文件名

        File file = new File(pathname, filename);
        FileInputStream fis;
        fis = new FileInputStream(file);

        long size = file.length();
        byte[] data = new byte[(int) size];
        fis.read(data, 0, (int) size);
        fis.close();
        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        out.write(data);
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/file/upload/logo", method = RequestMethod.POST)
    public Map<String, Object> upload(MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        if (file != null) {  //如果获取到的文件不为空
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String filename = "logo_" + username + ".jpg";  //根据用户名生成文件名
            File file_server = new File(pathname, filename);  //创建文件对象
            if (!file_server.getParentFile().exists()) {
                //如果文件父目录不存在，就创建这样一个目录
                file_server.getParentFile().mkdirs();
                System.out.println("创建目录" + file);
            } else {  //如果父文件夹已经存在

            }
            if (file_server.exists()) {
                file_server.delete();  //如果这个文件已经存在了，删除掉，重新上传
            } else {

            }
            file.transferTo(file_server);
            map.put("status", true);
            map.put("msg", "上传文件成功");
        } else {   //如果获取到的文件为空
            map.put("status", false);
            map.put("msg", "上传文件失败");
            map.put("reason", "文件为空");
        }
        return map;
    }

}
