package com.mystr.databaseDesign.Dao;

import com.mystr.databaseDesign.Entities.Airline;
import com.mystr.databaseDesign.Entities.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface StopDao extends JpaRepository<Stop,Integer>{
    List<Stop> findAll();


    Stop findById(int id);

    @Query(value = "select * from stop where airline_id = ?1 order by stop_num", nativeQuery = true)
    List<Stop> findAirlineStops(int airlineId);

    @Override
    void deleteById(Integer integer);
    void deleteByAirline(Airline airline);

}
