package com.mystr.databaseDesign.service;

import com.mystr.databaseDesign.Authorization.Realm;
import com.mystr.databaseDesign.Dao.AccountRepository;
import com.mystr.databaseDesign.Dao.MenuDao;
import com.mystr.databaseDesign.Entities.Account;
import com.mystr.databaseDesign.Entities.Admin.AccountRole;
import com.mystr.databaseDesign.Entities.Admin.Menu;
import com.mystr.databaseDesign.Entities.Admin.RoleMenu;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuService {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRoleService accountRoleService;
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    MenuDao menuDao;

    public List<Menu> getAll() {return menuDao.findAll();}

    public List<Menu> getMenusByCurrentUser() {
        // 从数据库中获取当前用户
        //HashMap<String,Object> accountMap = realm.getCurrentUserData();
        //String username = (String) accountMap.get("username");
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        Account account = accountService.getByName(username);

        // 获得当前用户对应的所有角色的 id 列表
        List<Integer> rids = accountRoleService.listAllByAid(account.getId())
                .stream().map(AccountRole::getRid).collect(Collectors.toList());

        // 查询出这些角色对应的所有菜单项
        List<Integer> menuIds = roleMenuService.findAllByRid(rids)
                .stream().map(RoleMenu::getMid).collect(Collectors.toList());
        List<Menu> menus = menuDao.findAllById(menuIds).stream().distinct().collect(Collectors.toList());

        return menus;
    }

}
