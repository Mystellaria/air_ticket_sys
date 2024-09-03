package com.mystr.databaseDesign.Dao;

import com.mystr.databaseDesign.Entities.Airline;
import com.mystr.databaseDesign.Entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface FlightDao extends JpaRepository<Flight,Integer>{
    List<Flight> findAll();
    Flight findByFlightId(int flightId);

    List<Flight> findByAirline(Airline airline);

    List<Flight> findByAirline_AirlineNumContainsIgnoreCase(String airlineNum);


    List<Flight> findByState(int state);

    List<Flight> findByFlightNum(String flightNum);

    List<Flight> findByFlightNumContainsIgnoreCase(String flightNum);

    List<Flight> findByAirline_DepartureContains(String departure);

    List<Flight> findByAirline_DestinationContains(String destination);

    @Query(value = "SELECT * FROM flight,airline,plane WHERE flight.airline_id=airline.airline_id AND airline.plane_id=plane.plane_id AND x_order_count >= x_capacity AND y_order_count >= y_capacity;", nativeQuery = true)
    List<Flight> findFull();


    List<Flight> findByFlightDate(Date flightDate);

    List<Flight> findByFlightDateBetween(Date flightDateStart, Date flightDateEnd);

    boolean existsByFlightId(int flightId);

    boolean existsByFlightNum(String flightNum);

    boolean existsByAirline_AirlineId(int airlineId);



    void deleteByFlightId(int flightId);
    void deleteByState(int state);



}