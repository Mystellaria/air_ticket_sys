package com.mystr.databaseDesign.Entities.Admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.mystr.databaseDesign.Entities.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "account_role")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @OneToOne
    @JoinColumn(name = "aid")
    Account account;

    @OneToOne
    @JoinColumn(name = "rid")
    Role role;

    public int getRid(){return role.getId();}
}
