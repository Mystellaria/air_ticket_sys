package com.mystr.databaseDesign.Dao;

import com.mystr.databaseDesign.Entities.Admin.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role,Integer> {
    Role findById(int id);

    List<Role> findAll();

}
