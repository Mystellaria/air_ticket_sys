package com.mystr.databaseDesign.controller;

import com.mystr.databaseDesign.Entities.Admin.Menu;
import com.mystr.databaseDesign.Result.Result;
import com.mystr.databaseDesign.Result.ResultFactory;
import com.mystr.databaseDesign.service.AccountService;
import com.mystr.databaseDesign.service.AdminMenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MenuController {
    @Autowired
    AdminMenuService adminMenuService;
    @Autowired
    AccountService accountService;

    @CrossOrigin
    @ResponseBody
    @GetMapping("/api/menu")
    public List<Menu> menu() {
        return adminMenuService.getMenusByCurrentUser();
        //return adminMenuService.getAll();
    }

    @ResponseBody
    @GetMapping("/api/menus")
    public Result menus() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        //boolean auth = SecurityUtils.getSubject().isAuthenticated();
        return ResultFactory.buildSuccessResult(username);
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/api/authentication")
    public Result auth() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        if (accountService.isExist(username)){
            System.out.println("Success Auth");
            return ResultFactory.buildSuccessResult(username);
        }
        else
            return ResultFactory.buildFailResult("Authentication error: Invalid username");
    }
}
