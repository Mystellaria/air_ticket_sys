package com.mystr.databaseDesign.Dao;

import com.mystr.databaseDesign.Entities.Admin.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRoleDao extends JpaRepository<AccountRole,Integer>{
    List<AccountRole> findAll();
    List<AccountRole> findByAccount_Id(int id);

    List<AccountRole> findByAccount_UsernameContainsIgnoreCase(String username);



    List<AccountRole> findByRole_Id(int id);


}
