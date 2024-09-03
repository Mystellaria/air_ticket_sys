package com.mystr.databaseDesign.Dao;

import com.mystr.databaseDesign.Entities.Airline;
import com.mystr.databaseDesign.Entities.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface AirlineDao extends JpaRepository<Airline,Integer>{
    List<Airline> findAll();
    Airline findByAirlineId(int airlineId);
    List<Airline> findAllByPlane(Plane plane);
    List<Airline> findAllByDeparture(String departure);
    List<Airline> findAllByDestination(String destination);
    List<Airline> findByDepartureAndDestination(String departure, String destination);
    List<Airline> findAllByActive(int active);

    boolean existsByAirlineId(int airlineId);

    boolean existsByAirlineNum(String airlineNum);

    boolean existsByPlane_PlaneId(int planeId);



    List<Airline> findByPlane_PlaneNum(String planeNum);



    List<Airline> findByAirlineNumContainsIgnoreCase(String airlineNum);

    //@Query(value = "SELECT a.* FROM airline AS a NATURAL JOIN plane AS p WHERE p.plane_num = ?1", nativeQuery = true)
    //List<Airline> findByPlaneNum(String planeNum);

    @Query(value = "select * from airline where x_price >= ?1 and x_price <= ?2", nativeQuery = true)
    List<Airline> findByXPriceBetween(int XPriceStart, int XPriceEnd);

    @Query(value = "select * from airline where y_price >= ?1 and y_price <= ?2", nativeQuery = true)
    List<Airline> findByYPriceBetween(int YPriceStart, int YPriceEnd);

    List<Airline> findByTakeOffTimeBetween(Time takeOffTimeStart, Time takeOffTimeEnd);

    List<Airline> findByArriveTimeBetween(Time arriveTimeStart, Time arriveTimeEnd);

    void deleteByAirlineId(int airlineId);

}

