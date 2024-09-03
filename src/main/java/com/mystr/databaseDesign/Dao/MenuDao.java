package com.mystr.databaseDesign.Dao;

import com.mystr.databaseDesign.Entities.Admin.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends JpaRepository<Menu,Integer>{
    List<Menu> findAll();
    Menu findById(int id);
}

