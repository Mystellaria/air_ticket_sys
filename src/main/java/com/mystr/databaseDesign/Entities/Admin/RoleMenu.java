package com.mystr.databaseDesign.Entities.Admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "role_menu")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "rid")
    Role role;

    @OneToOne
    @JoinColumn(name = "mid")
    Menu menu;

    public int getMid(){return menu.getId();}
}