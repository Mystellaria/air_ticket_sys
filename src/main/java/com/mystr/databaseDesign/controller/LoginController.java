package com.mystr.databaseDesign.controller;
import com.mystr.databaseDesign.Entities.Account;
import com.mystr.databaseDesign.Result.Result;
import com.mystr.databaseDesign.Result.ResultFactory;
import com.mystr.databaseDesign.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;


import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {
    @Autowired
    AccountService accountService;

    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody Account requestAccount,HttpSession session) {
        String username = requestAccount.getUsername();
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestAccount.getPassword());
        //username = HtmlUtils.htmlEscape(username);
        Account account = accountService.get(username,requestAccount.getPassword());
        try {
            subject.login(usernamePasswordToken);
            session.setAttribute("account", account);
            return ResultFactory.buildSuccessResult(username);
        } catch (Exception e) {
            String message = "账号密码错误";
            return ResultFactory.buildFailResult(message);
        }
    }



    @CrossOrigin
    @ResponseBody
    @GetMapping("api/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }



}
