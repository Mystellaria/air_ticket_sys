package com.mystr.databaseDesign.Dao;

import com.mystr.databaseDesign.Entities.Admin.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoleMenuDao extends JpaRepository<RoleMenu,Integer>{
    List<RoleMenu> findAll();

    List<RoleMenu> findByRole_Id(int id);

    List<RoleMenu> findByRole_IdIn(List<Integer> rids);




}
