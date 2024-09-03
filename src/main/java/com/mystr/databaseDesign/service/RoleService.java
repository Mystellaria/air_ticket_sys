package com.mystr.databaseDesign.service;

import com.mystr.databaseDesign.Dao.RoleDao;
import com.mystr.databaseDesign.Entities.Admin.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleDao roleDao;

    public List<Role> getAll() {return roleDao.findAll();}
    public Role getById(int id) {return roleDao.findById(id);}
}
