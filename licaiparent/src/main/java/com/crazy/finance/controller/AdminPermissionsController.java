package com.crazy.finance.controller;

import com.crazy.finance.bean.AdminPermissions;
import com.crazy.finance.service.AdminPermissionsService;
import com.crazy.finance.service.PermissionService;
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
public class AdminPermissionsController {
    @Autowired
    private AdminPermissionsService adminPermissionsService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 管理员权限管理界面
     * @param model
     * @return
     */
    @GetMapping("/admin/permission/toAdminPermissions.html")
    public String toAdminPermission(Model model) {
        List<AdminPermissions> list = adminPermissionsService.selectAdminPermissionsByAdminId(1);
        ArrayList<String> list2 = new ArrayList<>();
        for (AdminPermissions adminPermissions : list) {
            list2.add(adminPermissions.getPermissions().getPermission());
        }
        model.addAttribute("permissionsList", list2);


        model.addAttribute("activeUrl1", "permissionActive");
        model.addAttribute("activeUrl2", "adminPermissionsActive");
        model.addAttribute("pageTopBarInfo", "管理员权限管理界面");
        return "admin/permission/adminpermissions";
    }

    /**
     * 更新管理员权限
     * @param adminPermissions
     * @return
     */
    @PutMapping("/admin/updateAdminPermissions")
    @ResponseBody
    public JsonUtils updateAdminPermissions(@RequestParam("adminPermissions") String adminPermissions) {
        String[] permissionNameArray = adminPermissions.split(";");
        adminPermissionsService.updateAllAdminPermissions();
        for (String permissionName : permissionNameArray) {
            AdminPermissions ap = new AdminPermissions();
            ap.setAdminId(1);
            ap.setPermissionId(permissionService.selectPermissionsByPermission(permissionName).getId());
            adminPermissionsService.updateAdminPermissions(ap);
        }
        return JsonUtils.success();
    }
}
