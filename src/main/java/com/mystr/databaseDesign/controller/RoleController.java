package com.mystr.databaseDesign.controller;


import com.mystr.databaseDesign.Entities.Account;
import com.mystr.databaseDesign.Entities.Admin.Role;
import com.mystr.databaseDesign.Entities.Flight;
import com.mystr.databaseDesign.Result.Result;
import com.mystr.databaseDesign.Result.ResultFactory;
import com.mystr.databaseDesign.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    RoleService roleService;


    @CrossOrigin
    @GetMapping("/api/roles")
    public List<Role> list() throws Exception{
        return roleService.getAll();
    }
}
