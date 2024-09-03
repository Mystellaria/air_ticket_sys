package com.mystr.databaseDesign.service;

import com.mystr.databaseDesign.Dao.AccountRoleDao;
import com.mystr.databaseDesign.Entities.Account;
import com.mystr.databaseDesign.Entities.Admin.AccountRole;
import com.mystr.databaseDesign.Entities.Admin.Role;
import com.mystr.databaseDesign.utils.StringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountRoleService {
    @Autowired
    AccountRoleDao accountRoleDao;

    public List<AccountRole> getAll() {return accountRoleDao.findAll();}

    public List<AccountRole> listAllByAid(int aid) {
        return accountRoleDao.findByAccount_Id(aid);
    }

    public List<AccountRole> getByRid(int rid) {
        return accountRoleDao.findByRole_Id(rid);
    }

    public List<AccountRole> search(String keyword) {
        String[] keywords = StringFactory.StringSplitterSpace(keyword);
        List<AccountRole> resultA = new ArrayList<AccountRole>();
        List<AccountRole> resultB = new ArrayList<AccountRole>();
        List<AccountRole> temp;
        boolean useId = false;
        boolean useName = false;
        for(String s : keywords){
            if(s.startsWith("#")){
                temp = accountRoleDao.findByAccount_Id(Integer.parseInt(s.substring(1)));
                resultA.removeAll(temp);
                resultA.addAll(temp);
                useId = true;
            }
            if(s.startsWith("U/")){
                temp =  accountRoleDao.findByAccount_UsernameContainsIgnoreCase(s.substring(2));
                resultB.removeAll(temp);
                resultB.addAll(temp);
                useName = true;
            }
        }
        resultA = checkVoid(resultA,useId);
        resultB = checkVoid(resultB,useName);
        resultA.retainAll(resultB);
        return resultA;
    }

    public void grant(Account account, Role role){
        AccountRole accountRole = new AccountRole();
        accountRole.setAccount(account);
        accountRole.setRole(role);
        accountRoleDao.save(accountRole);
    }


    private List<AccountRole> checkVoid(List<AccountRole> input,boolean checker){
        if(!checker)
            return getAll();
        else
            return input;
    }
}
