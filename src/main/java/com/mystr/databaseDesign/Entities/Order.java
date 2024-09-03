package com.mystr.databaseDesign.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.*;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    int orderId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(name = "x_num")
    int XNum;
    @Column(name = "y_num")
    int YNum;
    @Column(name = "if_x_near")
    int ifXNear;
    @Column(name = "if_y_near")
    int ifYNear;
    @Column(name = "total_x_price")
    double totalXPrice;
    @Column(name = "total_y_price")
    double totalYPrice;

    private Timestamp time;
    int state;
}
