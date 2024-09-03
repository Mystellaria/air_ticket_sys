package com.mystr.databaseDesign.service;

import com.mystr.databaseDesign.Dao.AccountRepository;
import com.mystr.databaseDesign.Dao.OrderDao;
import com.mystr.databaseDesign.Entities.Account;
import com.mystr.databaseDesign.Entities.Flight;
import com.mystr.databaseDesign.utils.StringFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    OrderDao orderDao;

    public List<Account> getAll() {return accountRepository.findAll();}
    public Account getByName(String username) {
        return accountRepository.findByUsername(username);
    }
    public Account getById(int id) {
        return accountRepository.findById(id);
    }
    public Account get(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    public void deleteById(int id) throws Exception{
        if(deleteChecker(id))
            accountRepository.deleteById(id);
    }
    public boolean isExist(String username){
        return accountRepository.existsByUsername(username);
    }
    
    


    public void addOrUpdate(Account account) throws Exception{
        if(accountRepository.existsById(account.getId())) //修改
            accountRepository.save(account);
        else{   //添加
            if(addChecker(account))
                accountRepository.save(account);
            else
                throw new Exception();
        }
    }

    private boolean addChecker(Account account){
        if(accountRepository.existsByUsername(account.getUsername()))
            return false;
        return true;
    }

    private boolean deleteChecker(int id) throws Exception{
        if(orderDao.existsByAccount_Id(id))
            throw new IllegalAccessException();
        return true;
    }

    /*
    public HashMap<String, Object> getCurrentUserData() {
        Account user = (Account) SecurityUtils.getSubject().getPrincipal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("salt", user.getSalt());
        return map;
    }
    */
}

