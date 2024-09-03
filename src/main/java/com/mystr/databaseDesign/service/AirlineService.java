package com.mystr.databaseDesign.service;

import com.mystr.databaseDesign.Dao.AirlineDao;
import com.mystr.databaseDesign.Dao.FlightDao;
import com.mystr.databaseDesign.Entities.Airline;
import com.mystr.databaseDesign.Entities.Plane;
import com.mystr.databaseDesign.utils.StringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class AirlineService {
    @Autowired
    AirlineDao airlineDao;
    @Autowired
    FlightDao flightDao;
    
    public List<Airline> getAll() {return airlineDao.findAll();}
    public Airline getByAirlineId(int airlineId) {return airlineDao.findByAirlineId(airlineId);}
    public List<Airline> getByPlane(Plane plane) {return airlineDao.findAllByPlane(plane);}
    public List<Airline> getByDeparture(String departure) {return airlineDao.findAllByDeparture(departure);}
    public List<Airline> getByDestination(String destination) {return airlineDao.findAllByDestination(destination);}
    public List<Airline> getByDepartureAndDestination(String departure, String destination) {return airlineDao.findByDepartureAndDestination(departure,destination);}
    public List<Airline> getByActive(int active) {return airlineDao.findAllByActive(active);}

    public List<Airline> getByXPriceBetween(int XPriceStart, int XPriceEnd) {return airlineDao.findByXPriceBetween(XPriceStart,XPriceEnd);}

    public List<Airline> getByYPriceBetween(int YPriceStart, int YPriceEnd) {return airlineDao.findByYPriceBetween(YPriceStart,YPriceEnd);}

    public List<Airline> getByTakeOffTimeBetween(Time takeOffTimeStart, Time takeOffTimeEnd) {return airlineDao.findByTakeOffTimeBetween(takeOffTimeStart,takeOffTimeEnd);}

    public List<Airline> getByArriveTimeBetween(Time arriveTimeStart, Time arriveTimeEnd) {return airlineDao.findByArriveTimeBetween(arriveTimeStart,arriveTimeEnd);}

    public void deleteByAirlineId(int airlineId) throws Exception{
        if(deleteChecker(airlineId))
            airlineDao.deleteByAirlineId(airlineId);
    }


    public List<Airline> search(String keyword) {
        String[] keywords = StringFactory.StringSplitterSpace(keyword);
        List<Airline> resultA = new ArrayList<Airline>();
        List<Airline> resultB = new ArrayList<Airline>();
        List<Airline> resultC = new ArrayList<Airline>();
        List<Airline> resultD = new ArrayList<Airline>();
        List<Airline> temp;
        boolean useNum = false;
        boolean usePNum = false;
        boolean useFrom = false;
        boolean useTo = false;
        for(String s : keywords){
            if(s.startsWith("#")){
                temp = airlineDao.findByAirlineNumContainsIgnoreCase(s.substring(1));
                resultA.removeAll(temp);
                resultA.addAll(temp);
                useNum = true;
            }
            if(s.startsWith("P#")){
                temp =  airlineDao.findByPlane_PlaneNum(s.substring(2));
                resultB.removeAll(temp);
                resultB.addAll(temp);
                usePNum = true;
            }
            if(s.startsWith("F/")){
                temp =  airlineDao.findAllByDeparture(s.substring(2));
                resultC.removeAll(temp);
                resultC.addAll(temp);
                useFrom = true;
            }
            if(s.startsWith("T/")){
                temp = airlineDao.findAllByDestination(s.substring(2));
                resultD.removeAll(temp);
                resultD.addAll(temp);
                useTo = true;
            }
        }
        resultA = checkVoid(resultA,useNum);
        resultB = checkVoid(resultB,usePNum);
        resultC = checkVoid(resultC,useFrom);
        resultD = checkVoid(resultD,useTo);
        resultA.retainAll(resultB);
        resultC.retainAll(resultD);
        resultA.retainAll(resultC);
        return resultA;
    }

    public void addOrUpdate(Airline airline) throws Exception{
        if(airlineDao.existsByAirlineId(airline.getAirlineId())) //修改
            airlineDao.save(airline);
        else{   //添加
            if(addChecker(airline))
                airlineDao.save(airline);
            else
                throw new Exception();
        }
    }

    private List<Airline> checkVoid(List<Airline> input,boolean checker){
        if(!checker)
            return getAll();
        else
            return input;
    }

    private boolean addChecker(Airline airline){
        if(airlineDao.existsByAirlineNum(airline.getAirlineNum()))
            return false;
        return true;
    }

    private boolean deleteChecker(int airline_id) throws Exception{
        if(flightDao.existsByAirline_AirlineId(airline_id))
            throw new IllegalAccessException();
        return true;
    }
}
