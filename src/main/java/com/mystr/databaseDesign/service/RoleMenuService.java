package com.mystr.databaseDesign.service;

import com.mystr.databaseDesign.Dao.RoleMenuDao;
import com.mystr.databaseDesign.Entities.Admin.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMenuService {
    @Autowired
    RoleMenuDao roleMenuDao;
    public List<RoleMenu> findByRid(int rid){
        return roleMenuDao.findByRole_Id(rid);
    }
    public List<RoleMenu> findAllByRid(List<Integer> rids){
        return roleMenuDao.findByRole_IdIn(rids);
    }
}
