package com.mystr.databaseDesign.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "plane")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plane_id")
    private int planeId;
    private String planeNum;
    private String type;
    @Column(name = "x_capacity")
    private int XCapacity;
    @Column(name = "y_capacity")
    private int YCapacity;
    private int state;


}
