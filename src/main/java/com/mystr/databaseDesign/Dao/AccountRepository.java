package com.mystr.databaseDesign.Dao;

import com.mystr.databaseDesign.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{



    List<Account> findAll();

    Account findById(int id);

    boolean existsById(int id);
    boolean existsByUsername(String username);
    Account findByUsername(String username);
    Account findByUsernameAndPassword(String username, String password);

    void deleteById(int id);
}
