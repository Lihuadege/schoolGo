package com.li.schoolGo.controller;

import com.li.schoolGo.constant.ImagePath;
import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.bean.SysUser;
import com.li.schoolGo.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@Controller
@Slf4j
public class SetController {

    @Autowired
    SysUserService sysUserService;

    @RequestMapping("basicInfo")
    public String basicInfo(HttpServletRequest request){
        String userId = (String) request.getAttribute("userId");
        SysUser user = sysUserService.getSysUserById(userId);
        request.setAttribute("user",user);
        return "personal/info";
    }

    @RequestMapping("toUpdatePwd")
    public String updatePwd(HttpServletRequest request){
        String userId = (String) request.getAttribute("userId");
        SysUser user = sysUserService.getSysUserById(userId);
        request.setAttribute("user",user);
        return "personal/password";
    }

    @RequestMapping("updatePwd")
    @ResponseBody
    public ResponseBean updatePwd(String id, String oldPassword, String password, String repassword){
        if(id == null || oldPassword == null || password == null || repassword == null){
            return ResponseBean.baseError("出错了");
        }

        if(!password.equals(repassword)){
            return ResponseBean.baseError("输入新密码不一致");
        }

        Boolean res = sysUserService.getSysUser(id, oldPassword);
        if(!res){
            return ResponseBean.baseError("旧密码输入有误");
        }

        Boolean result = sysUserService.updateUserPwd(id,password);
        if(result){
            return ResponseBean.baseSuccess("修改成功");
        }
        return ResponseBean.baseError("修改失败");
    }

    @RequestMapping("updateUser")
    @ResponseBody
    public ResponseBean updateUser(SysUser user) {
        log.debug("user: " + user);
        int i = sysUserService.updateManagerById(user);
        if (i == 1) {
            sysUserService.updateRedisDate(user.getId());
            return ResponseBean.baseSuccess("修改成功");
        } else {
            return ResponseBean.baseError("修改失败");
        }
    }

    /**
     * 图片上传功能
     *
     * @param file
     * @param id
     * @return
     */
    @RequestMapping("uploadHead")
    @ResponseBody
    public ResponseBean uploadHeadImg(@RequestParam(name = "file", required = false) MultipartFile file, String id) {

        if (file == null || file.isEmpty()) {
            return ResponseBean.baseError("上传图片为空");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(new Date());
        String name = file.getOriginalFilename();
        int i = new Random().nextInt() * 3;

        String fileName = format + i + name;

        FileOutputStream fo = null;
        String relativePath = ImagePath.HEAD_IMG_PATH + "/" + fileName;
        try {
            URL path = ResourceUtils.getURL("classpath:");
            String classpath = URLDecoder.decode(path.getPath(), "UTF-8");

            File filePath = new File(classpath + "static" + ImagePath.HEAD_IMG_PATH);
            if(!filePath.exists()){
                filePath.mkdir();
            }

            fo = new FileOutputStream(classpath + "static" + relativePath);

            fo.write(file.getBytes());

            SysUser sysUser = new SysUser();
            sysUser.setId(id);
            sysUser.setHeadImg(relativePath);

            int res = sysUserService.updateManagerById(sysUser);
            sysUserService.updateRedisDate(id);

            log.debug("用户修改头像，路径：" + classpath + "static" + relativePath);

            if (fo != null) fo.close();

            if (res != 1) {
                return ResponseBean.baseError("上传失败");
            }
        } catch (IOException e) {
            log.debug(e.getMessage());
            return ResponseBean.baseTransfer();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("url", relativePath);

        return ResponseBean.baseSuccess("上传成功", map);
    }

}
