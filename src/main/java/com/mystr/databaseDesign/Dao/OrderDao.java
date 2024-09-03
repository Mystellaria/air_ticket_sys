package com.mystr.databaseDesign.Dao;

import com.mystr.databaseDesign.Entities.Account;
import com.mystr.databaseDesign.Entities.Flight;
import com.mystr.databaseDesign.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order,Integer> {
    List<Order> findAll();
    Order findByOrderId(int orderId);

    List<Order> getByOrderId(int orderId);

    List<Order> findByAccount(Account account);

    List<Order> findByAccount_UsernameContainsIgnoreCase(String username);

    List<Order> findByFlight(Flight flight);

    List<Order> findByFlight_FlightId(int flightId);



    List<Order> findByFlight_FlightNumContainsIgnoreCase(String flightNum);


    List<Order> findByState(int state);

    List<Order> findByTime(Timestamp time);

    boolean existsByOrderId(int orderId);

    boolean existsByAccount_Id(int id);

    boolean existsByFlight_FlightId(int flightId);




    boolean existsByAccount_IdAndFlight_FlightIdAndStateIsNot(int id, int flightId, int state);





    List<Order> findByTimeBetween(Timestamp timeStart, Timestamp timeEnd);
    List<Order> findByTimeBefore(Timestamp time);
    List<Order> findByTimeAfter(Timestamp time);

    void deleteByOrderId(int orderId);
    void deleteByState(int state);
    void deleteByTimeBefore(Timestamp time);






}
