package com.mystr.databaseDesign.controller;

import com.mystr.databaseDesign.Entities.Account;
import com.mystr.databaseDesign.Entities.Admin.AccountRole;
import com.mystr.databaseDesign.service.AccountRoleService;
import com.mystr.databaseDesign.utils.StringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountRoleController {
    @Autowired
    AccountRoleService accountRoleService;


    @CrossOrigin
    @GetMapping("/api/accountRoles")
    public List<AccountRole> list() throws Exception{
        return accountRoleService.getAll();
    }


    @CrossOrigin
    @GetMapping("/api/search/accountRoles")
    public List<AccountRole> listFilter(@RequestParam("roleCodes") String roleCodes, @RequestParam("keywords") String keywords) throws Exception {
        List<AccountRole> resultList = new ArrayList<AccountRole>();
        if (roleCodes!=null && !roleCodes.equals("")) {
            int[] codes = StringFactory.StringSplitter(roleCodes);
            for(int code : codes){
                List<AccountRole> addList = accountRoleService.getByRid(code);
                resultList.removeAll(addList);
                resultList.addAll(addList);
            }
            if (keywords != null && !keywords.equals("")) {
                resultList.retainAll(accountRoleService.search(keywords));
            }
        }
        else{
            if (keywords != null && !keywords.equals(""))
                resultList.addAll(accountRoleService.search(keywords));
            else
                return list();
        }
        return  resultList;
    }

}
