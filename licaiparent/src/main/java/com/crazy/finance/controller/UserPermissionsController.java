package com.crazy.finance.controller;

import com.crazy.finance.bean.UserPermissions;
import com.crazy.finance.service.PermissionService;
import com.crazy.finance.service.UserPermissionsService;
import com.crazy.finance.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.List;

@Controller
public class UserPermissionsController {
    @Autowired
    private UserPermissionsService userPermissionsService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 更新用户权限
     *
     * @param userPermissions
     * @return
     */
    @PutMapping("/admin/updateUserPermissions")
    @ResponseBody
    public JsonUtils updateUserPermissions(@RequestParam("userPermissions") String userPermissions) {
        String[] permissionArray = userPermissions.split(";");
        userPermissionsService.deleteAllUserPermissionsByUserId(1);
        for (String permissionName : permissionArray) {
            UserPermissions up = new UserPermissions();
            up.setUserId(1);
            up.setPermissionId(permissionService.selectPermissionsByPermission(permissionName).getId());
            userPermissionsService.insertUserPermissions(up);
        }
        return JsonUtils.success();
    }

    /**
     * 跳转到用户权限管理界面（管理员）
     *
     * @param model
     * @return
     */
    @GetMapping("/admin/permission/toUserPermissions.html")
    public String toUserPermission(Model model) {
        List<UserPermissions> list = userPermissionsService.selectUserPermissionsByUserId(1);
        ArrayList<String> list2 = new ArrayList<>();
        for (UserPermissions userPermissions : list) {
            list2.add(userPermissions.getPermissions().getPermission());
        }
        model.addAttribute("permissionsList", list2);


        model.addAttribute("activeUrl1", "permissionActive");
        model.addAttribute("activeUrl2", "userPermissionsActive");
        model.addAttribute("pageTopBarInfo", "用户权限管理界面");
        return "admin/permission/userpermissions";
    }
}
