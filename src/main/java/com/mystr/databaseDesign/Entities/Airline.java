package com.mystr.databaseDesign.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.*;

@Entity
@Table(name = "airline")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id")
    int airlineId;

    String airlineNum;

    //@Transient
    //String planeNum;

    @ManyToOne
    @JoinColumn(name = "plane_id")
    private Plane plane;
    String departure;
    String destination;
    private Time checkTime;
    private Time takeOffTime;
    private Time arriveTime;
    int active;
    @Column(name = "x_price")
    double XPrice;
    @Column(name = "y_price")
    double YPrice;
}
