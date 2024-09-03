package com.mystr.databaseDesign.service;

import com.mystr.databaseDesign.Dao.StopDao;
import com.mystr.databaseDesign.Entities.Airline;
import com.mystr.databaseDesign.Entities.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StopService {
    @Autowired
    StopDao stopDao;

    public List<Stop> getAll() {return stopDao.findAll();}
    public Stop getById(int id) {return stopDao.findById(id);}
    public List<Stop> getRoute(Airline airline) {
        List<Stop> result = new ArrayList<Stop>();
        result.add(new Stop(0,airline,0,airline.getDeparture(),null,airline.getTakeOffTime()));
        result.addAll(stopDao.findAirlineStops(airline.getAirlineId()));
        int DestinationNum = result.size();
        result.add(new Stop(Integer.MAX_VALUE,airline,DestinationNum,airline.getDestination(),airline.getArriveTime(),null));
        return result;
    }

    public void deleteById(int id) {stopDao.deleteById(id);}
    public void deleteByAirline(Airline airline) {stopDao.deleteByAirline(airline);}

    public void addOrUpdate(Stop stop) {stopDao.save(stop);}
}
