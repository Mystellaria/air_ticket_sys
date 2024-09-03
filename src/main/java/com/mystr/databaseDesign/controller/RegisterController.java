package com.mystr.databaseDesign.controller;

import com.mystr.databaseDesign.Entities.Account;
import com.mystr.databaseDesign.Entities.Admin.Role;
import com.mystr.databaseDesign.Result.Result;
import com.mystr.databaseDesign.Result.ResultFactory;
import com.mystr.databaseDesign.service.AccountRoleService;
import com.mystr.databaseDesign.service.AccountService;
import com.mystr.databaseDesign.service.RoleService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;


@Controller
public class RegisterController {
    @Autowired
    AccountService accountService;
    @Autowired
    RoleService roleService;
    @Autowired
    AccountRoleService accountRoleService;

    @CrossOrigin
    @PostMapping("api/register")
    @ResponseBody
    public Result register(@RequestBody Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        username = HtmlUtils.htmlEscape(username);
        account.setUsername(username);

        boolean exist = accountService.isExist(username);
        if (exist) {
            String message = "用户名已被使用";
            return ResultFactory.buildFailResult(message);
        }

        // 生成盐,默认长度 16 位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置 hash 算法迭代次数
        int times = 2;
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        // 存储用户信息，包括 salt 与 hash 后的密码
        account.setSalt(salt);
        account.setPassword(encodedPassword);
        try {
            accountService.addOrUpdate(account);
            Role role = roleService.getById(2);
            account = accountService.getByName(username);
            accountRoleService.grant(account,role);
        }
        catch (Exception e){
            return ResultFactory.buildFailResult("用户名已被使用");
        }

        return ResultFactory.buildSuccessResult(account);
    }

}
