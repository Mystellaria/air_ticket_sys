package com.mystr.databaseDesign.controller;


import com.mystr.databaseDesign.Entities.Account;
import com.mystr.databaseDesign.Entities.Flight;
import com.mystr.databaseDesign.Result.Result;
import com.mystr.databaseDesign.Result.ResultFactory;
import com.mystr.databaseDesign.service.AccountService;
import com.mystr.databaseDesign.utils.StringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;


    @CrossOrigin
    @GetMapping("/api/accounts")
    public List<Account> list() throws Exception{
        return accountService.getAll();
    }

    @CrossOrigin
    @PostMapping("/api/accounts")
    @ResponseBody
    public Result addOrUpdate(@RequestBody Account account) {
        try {
            accountService.addOrUpdate(account);
            return ResultFactory.buildSuccessResult(account);
        }
        catch (Exception e){
            return ResultFactory.buildFailResult("用户名已被使用");
        }
    }

    @CrossOrigin
    @PostMapping("/api/delete/accounts")
    @ResponseBody
    public Result delete(@RequestBody Account account) throws Exception{
        try {
            accountService.deleteById(account.getId());
            return ResultFactory.buildSuccessResult(account);
        }
        catch (IllegalAccessException e){
            return ResultFactory.buildFailResult("该用户名存在未删除的订单");
        }
    }



}
