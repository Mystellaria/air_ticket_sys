package com.mystr.databaseDesign.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.*;

@Entity
@Table(name = "flight")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    int flightId;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;

    String flightNum;
    private Date flightDate;
    int state;
    int xOrderCount;
    int yOrderCount;

    public void addCount(int x, int y){
        xOrderCount+=x;
        yOrderCount+=y;
    }
}
