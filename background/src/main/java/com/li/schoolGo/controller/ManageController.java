package com.li.schoolGo.controller;

import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.bean.SchoolInfo;
import com.li.schoolGo.bean.SysUser;
import com.li.schoolGo.service.SchoolInfoService;
import com.li.schoolGo.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ManageController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SchoolInfoService schoolInfoService;

    @RequestMapping("toUndelManager")
    public String toUndelManager() {
        return "user/undel";
    }

    @RequestMapping("addManager")
    @ResponseBody
    public ResponseBean doAddManager(SysUser sysUser) {
        if (sysUser.getId() == null || sysUser.getId().isEmpty()) {
            //用户id为空，新增用户
            sysUser.setId(null);
            String pwd = "123456";
            log.debug("新增用户" + sysUser);
            sysUser.setPassword(DigestUtils.md5Hex(pwd.getBytes()));
            int i = sysUserService.insertNewSysUser(sysUser);
            if (i == 1) {
                return ResponseBean.baseSuccess("新增成功");
            }
        } else {
            log.debug("修改用户" + sysUser);
            int i = sysUserService.updateManagerById(sysUser);
            if (i == 1) {
                return ResponseBean.baseSuccess("修改成功");
            }
        }
        return ResponseBean.baseError("操作失败，请重试");
    }

    @RequestMapping("listManager")
    @ResponseBody
    public Map<String, Object> getManagers(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "limit", defaultValue = "10") Integer pageSize,
                                           String loginName, String phoneNum, String email) {
        Map<String, Object> resultMap = sysUserService.getAll(pageNum, pageSize, loginName, phoneNum, email);

        if (resultMap.get("data") != null) {
            resultMap.put("code", 0);
            resultMap.put("msg", "获取成功");
        } else {
            resultMap.put("code", 500);
            resultMap.put("msg", "获取失败");
        }
        return resultMap;
    }

    @RequestMapping("listDelManager")
    @ResponseBody
    public Map<String, Object> getDelManagers(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "limit", defaultValue = "10") Integer pageSize,
                                              String loginName, String phoneNum, String email) {
        Map<String, Object> resultMap = sysUserService.getAllDel(pageNum, pageSize, loginName, phoneNum, email);

        resultMap.put("code", 0);
        if (resultMap.get("data") != null) {
            resultMap.put("msg", "获取成功");
        } else {
            resultMap.put("msg", "获取失败");
        }
        return resultMap;
    }

    /**
     * 删除功能，包含批量删除和单个删除
     *
     * @param checkId
     * @return
     */
    @RequestMapping("delManager")
    @ResponseBody
    public ResponseBean doDelManager(@RequestBody ArrayList<String> checkId) {

        Boolean res = sysUserService.delManager(checkId);

        if (res) return ResponseBean.baseSuccess("封禁成功");
        return ResponseBean.baseError("封禁失败，请重试");
    }

    @RequestMapping("undelManager")
    @ResponseBody
    public ResponseBean deUndelManager(String checkId) {
        Boolean res = sysUserService.undelManager(checkId);

        if (res) return ResponseBean.baseSuccess("解封成功");
        return ResponseBean.baseError("解封失败，请重试");
    }

    @RequestMapping("manageList")
    public String toManageList() {
        return "user/list";
    }

    @RequestMapping("toAddManager")
    public String toAddManager(String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object parentArea = session.getAttribute("parentArea");

        if (parentArea == null) {
            List<SchoolInfo> parent = schoolInfoService.getParent();

            session.setAttribute("parentArea", parent);
        }

        if (id != null) {
            SysUser user = sysUserService.getSysUserById(id);
            if (user != null) {
                Integer userAreaId = user.getUserAreaId();
                //此处根据用户所属学校id查询出与该用户所属地区相同的所有学校
                List<SchoolInfo> schoolInfos = schoolInfoService.getSchoolInfoListByUserAreaId(userAreaId);
                SchoolInfo parent = schoolInfoService.getParentId(userAreaId);
                request.setAttribute("parentId", parent.getParentId());

                request.setAttribute("schoolInfos", schoolInfos);

                request.setAttribute("user", user);
            }
        }
        return "user/userForm";
    }

}
