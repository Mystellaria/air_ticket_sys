package com.mystr.databaseDesign.service;

import com.mystr.databaseDesign.Dao.FlightDao;
import com.mystr.databaseDesign.Dao.OrderDao;
import com.mystr.databaseDesign.Entities.Flight;
import com.mystr.databaseDesign.Entities.Airline;
import com.mystr.databaseDesign.Entities.Order;
import com.mystr.databaseDesign.utils.State;
import com.mystr.databaseDesign.utils.StringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FlightService {
    @Autowired
    FlightDao flightDao;
    @Autowired
    OrderDao orderDao;
    final int RANGE_NUMBER = 4;

    public List<Flight> getAll() {return flightDao.findAll();}
    public List<Flight> getFull() {return flightDao.findFull();}
    public Flight getByFlightId(int flightId) {return flightDao.findByFlightId(flightId);}
    public List<Flight> getByAirline(Airline airline) {return flightDao.findByAirline(airline);}
    public List<Flight> getByState(int state) {return flightDao.findByState(state);}
    public List<Flight> getByFlightNum(String flightNum) {return flightDao.findByFlightNumContainsIgnoreCase(flightNum);}
    public List<Flight> getByDeparture(String departure) {return flightDao.findByAirline_DepartureContains(departure);}
    public List<Flight> getByDestination(String destination) {return flightDao.findByAirline_DestinationContains(destination);}
    public List<Flight> getByFlightDate(Date flightDate) {return flightDao.findByFlightDate(flightDate);}

    public List<Flight> getByFlightDateBetween(Date flightDateStart, Date flightDateEnd) {return flightDao.findByFlightDateBetween(flightDateStart,flightDateEnd);}

    public void deleteByFlightId(int flightId) {
        if(deleteChecker(flightId))
            flightDao.deleteByFlightId(flightId);
    }
    public void deleteCanceled() { flightDao.deleteByState(State.FLIGHT_CANCELED.code);}

    public List<Flight> search(String keyword) {
        String[] keywords = StringFactory.StringSplitterSpace(keyword);
        List<Flight> resultA = new ArrayList<Flight>();
        List<Flight> resultB = new ArrayList<Flight>();
        List<Flight> resultC = new ArrayList<Flight>();
        List<Flight> resultD = new ArrayList<Flight>();
        List<Flight> temp;
        String[] tempStr = new String[2*RANGE_NUMBER];
        boolean useNum = false;
        boolean useANum = false;
        boolean useDate = false;
        int useDateRange = 0;
        for(String s : keywords){
            if(s.startsWith("#")){
                temp = flightDao.findByFlightNumContainsIgnoreCase(s.substring(1));
                resultA.removeAll(temp);
                resultA.addAll(temp);
                useNum = true;
            }
            if(s.startsWith("A#")){
                temp =  flightDao.findByAirline_AirlineNumContainsIgnoreCase(s.substring(2));
                resultB.removeAll(temp);
                resultB.addAll(temp);
                useANum = true;
            }
            if(s.startsWith("D/")){
                temp = searchDate(s.substring(2));
                resultC.removeAll(temp);
                resultC.addAll(temp);
                useDate = true;
            }
            if(s.startsWith("R/")){
                if(useDateRange < 2*RANGE_NUMBER){
                    tempStr[useDateRange] = s.substring(2);
                    useDateRange++;
                }
            }
        }
        for(int i = 0; i <= useDateRange/2 -1 ; i++){
            temp = searchDateRange(tempStr[2*i],tempStr[2*i+1]);
            resultD.removeAll(temp);
            resultD.addAll(temp);
        }
        resultA = checkVoid(resultA,useNum);
        resultB = checkVoid(resultB,useANum);
        if(!useDate && useDateRange == 0)
            resultC = getAll();
        else{
            resultC.removeAll(resultD);
            resultC.addAll(resultD);
        }
        resultA.retainAll(resultB);
        resultA.retainAll(resultC);
        return resultA;
    }

    public void addOrUpdate(Flight flight) throws Exception{
        if(flightDao.existsByFlightId(flight.getFlightId())) //修改
            flightDao.save(flight);
        else{   //添加
            if(addChecker(flight))
                flightDao.save(flight);
            else
                throw new Exception();
        }
    }

    private List<Flight> checkVoid(List<Flight> input,boolean checker){
        if(!checker)
            return getAll();
        else
            return input;
    }

    private boolean addChecker(Flight flight){
        if(flightDao.existsByFlightNum(flight.getFlightNum()))
            return false;
        return true;
    }

    private boolean deleteChecker(int flight_id){
        if(orderDao.existsByFlight_FlightId(flight_id)){
            List<Order> list = orderDao.findByFlight_FlightId(flight_id);
            for(Order o : list){
                o.setFlight(null);
                orderDao.save(o);
            }
        }
        return true;
    }

    private List<Flight> searchDate(String key){
        try {
            Date date = Date.valueOf(key);
            return flightDao.findByFlightDate(date);
        }
        catch (IllegalArgumentException e){
            return getAll();
        }
    }

    private List<Flight> searchDateRange(String key1,String key2){
        try {
            Date date1 = Date.valueOf(key1);
            Date date2 = Date.valueOf(key2);
            return flightDao.findByFlightDateBetween(date1,date2);
        }
        catch (IllegalArgumentException e){
            return getAll();
        }
    }
}
