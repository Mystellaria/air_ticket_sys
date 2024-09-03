package com.mystr.databaseDesign.service;

import com.mystr.databaseDesign.Dao.AccountRepository;
import com.mystr.databaseDesign.Dao.AccountRoleDao;
import com.mystr.databaseDesign.Dao.FlightDao;
import com.mystr.databaseDesign.Dao.OrderDao;
import com.mystr.databaseDesign.Entities.Airline;
import com.mystr.databaseDesign.Entities.Account;
import com.mystr.databaseDesign.Entities.Flight;
import com.mystr.databaseDesign.Entities.Order;
import com.mystr.databaseDesign.utils.State;
import com.mystr.databaseDesign.utils.StringFactory;
import org.apache.shiro.SecurityUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    FlightDao flightDao;
    @Autowired
    AccountRepository accountRepository;
    final int RANGE_NUMBER = 4;

    public List<Order> getAll() {return orderDao.findAll();}
    public Order getByOrderId(int orderId) {return orderDao.findByOrderId(orderId);}
    public List<Order> getMy() {
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        Account account = accountRepository.findByUsername(username);
        return orderDao.findByAccount(account);
    }
    public List<Order> getByFlight(Flight flight) {return orderDao.findByFlight(flight);}
    public List<Order> getByState(int state) {return orderDao.findByState(state);}

    public List<Order> getByTimeBetween(Timestamp timeStart, Timestamp timeEnd) {return orderDao.findByTimeBetween(timeStart,timeEnd);}
    public List<Order> getByTimeBefore(Timestamp time) {return orderDao.findByTimeBefore(time);}
    public List<Order> getByTimeAfter(Timestamp time) {return orderDao.findByTimeAfter(time);}

    public void deleteByOrderId(int orderId) { orderDao.deleteByOrderId(orderId);}
    public void deleteFinished() {orderDao.deleteByState(State.ORDER_FINISHED.code);}
    public void deleteCanceled() {orderDao.deleteByState(State.ORDER_CANCELED.code);}
    public void deleteBefore(Timestamp time) {orderDao.deleteByTimeBefore(time);}

    public void cancelByOrderId(int orderId) throws Exception{
        Order order = getByOrderId(orderId);
        if(timeChecker(order)){
            order.setState(502);
            orderDao.save(order);
        }
        else
            throw new Exception();
    }


    public List<Order> search(String keyword) {
        String[] keywords = StringFactory.StringSplitterSpace(keyword);
        List<Order> resultA = new ArrayList<Order>();
        List<Order> resultB = new ArrayList<Order>();
        List<Order> resultC = new ArrayList<Order>();
        List<Order> resultD = new ArrayList<Order>();
        List<Order> resultE = new ArrayList<Order>();
        List<Order> temp;
        String[] tempStr = new String[2*RANGE_NUMBER];
        boolean useOID = false;
        boolean useUName = false;
        boolean useFID = false;
        boolean useTime = false;
        int useTimeRange = 0;
        for(String s : keywords){
            if(s.startsWith("#")){
                temp = orderDao.getByOrderId(Integer.parseInt(s.substring(1)));
                resultA.removeAll(temp);
                resultA.addAll(temp);
                useOID = true;
            }
            if(s.startsWith("U/")){
                temp =  orderDao.findByAccount_UsernameContainsIgnoreCase(s.substring(2));
                resultB.removeAll(temp);
                resultB.addAll(temp);
                useUName = true;
            }
            if(s.startsWith("F#")){
                temp = orderDao.findByFlight_FlightNumContainsIgnoreCase(s.substring(2));
                resultC.removeAll(temp);
                resultC.addAll(temp);
                useFID = true;
            }
            if(s.startsWith("D/")){
                temp = searchTime(s.substring(2));
                resultD.removeAll(temp);
                resultD.addAll(temp);
                useTime = true;
            }
            if(s.startsWith("R/")){
                if(useTimeRange < 2*RANGE_NUMBER){
                    tempStr[useTimeRange] = s.substring(2);
                    useTimeRange++;
                }
            }
        }
        for(int i = 0; i <= useTimeRange/2 -1 ; i++){
            temp = searchTimeRange(tempStr[2*i],tempStr[2*i+1]);
            resultE.removeAll(temp);
            resultE.addAll(temp);
        }
        List<Order> none = new ArrayList<Order>();
        resultA = checkVoid(resultA,useOID);
        resultB = checkVoid(resultB,useUName);
        resultC = checkVoid(resultC,useFID);
        if(!useTime && useTimeRange==0)
            resultD = getAll();
        else{
            resultD.removeAll(resultE);
            resultD.addAll(resultE);
        }
        resultA.retainAll(resultB);
        resultC.retainAll(resultD);
        resultA.retainAll(resultC);
        return resultA;
    }

    public void addOrUpdate(Order order) throws Exception{
        Order old;
        Flight flight = order.getFlight();
        Account account;
        if(orderDao.existsByOrderId(order.getOrderId()) ){ //修改
            old = orderDao.findByOrderId(order.getOrderId());
            if(timeChecker(old)){
                flight = flightDao.findByFlightId(flight.getFlightId());
                int xChange = order.getXNum() - old.getXNum();
                int yChange = order.getYNum() - old.getYNum();
                flight.addCount(xChange,yChange);
                old.setFlight(flight);
                old.setXNum(order.getXNum());
                old.setYNum(order.getYNum());
                old.setTotalXPrice(order.getTotalXPrice());
                old.setTotalYPrice(order.getTotalYPrice());
                flightDao.save(flight);
                orderDao.save(old);
            }
        }
        else{   //添加
            String username = (String) SecurityUtils.getSubject().getPrincipal();
            account = accountRepository.findByUsername(username);
            order.setAccount(account);
            order.setTime(new Timestamp(System.currentTimeMillis()));
            flight = flightDao.findByFlightId(flight.getFlightId());
            flight.addCount(order.getXNum(),order.getYNum());
            order.setFlight(flight);
            if(addChecker(order)){
                flightDao.save(flight);
                orderDao.save(order);
            }
        }
    }

    private List<Order> checkVoid(List<Order> input,boolean checker){
        if(!checker)
            return getAll();
        else
            return input;
    }

    private boolean addChecker(Order order) throws Exception{
        if(orderDao.existsByAccount_IdAndFlight_FlightIdAndStateIsNot(order.getAccount().getId(),order.getFlight().getFlightId(),502))
            throw new IllegalAccessException();
        if(!timeChecker(order))
            throw new UnsupportedOperationException();
        return true;
    }

    private boolean timeChecker(Order order){
        String date = order.getFlight().getFlightDate().toString();
        String takeOff = order.getFlight().getAirline().getTakeOffTime().toString();
        Timestamp takeOffTime = Timestamp.valueOf(date + " " + takeOff);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.HOUR_OF_DAY,1);
        Timestamp after = new Timestamp(cal.getTimeInMillis());
        return after.before(takeOffTime);
    }

    private List<Order> searchTime(String key){
        try {
            return formatTime(key);
        }
        catch (IllegalArgumentException e){
            return getAll();
        }
    }

    private List<Order> searchTimeRange(String key1,String key2){
        try {
            return formatTimeRange(key1,key2);
        }
        catch (IllegalArgumentException e){
            return getAll();
        }
    }

    private List<Order> formatTime(String key){
        Timestamp startTime,endTime;
        try {
            startTime = Timestamp.valueOf(key);
            return orderDao.findByTime(startTime);
        }
        catch (IllegalArgumentException e){
            startTime = Timestamp.valueOf(key + " 00:00:00");
            endTime = Timestamp.valueOf(key + " 23:59:59");
            return orderDao.findByTimeBetween(startTime,endTime);
        }
    }

    private List<Order> formatTimeRange(String key1,String key2){
        Timestamp startTime,endTime;
        String start,end;
        try {
            if(!key1.equals(""))
                startTime = Timestamp.valueOf(key1);
            else
                startTime = null;
        }
        catch (IllegalArgumentException e){
            start = key1 + " 00:00:00";
            startTime = Timestamp.valueOf(start);
        }
        try {
            if(!key2.equals(""))
                endTime = Timestamp.valueOf(key2);
            else
                endTime = null;
        }
        catch (IllegalArgumentException e){
            end = key2 + " 23:59:59";
            endTime = Timestamp.valueOf(end);
        }
        if(key1.equals("")&&key2.equals(""))
            return getAll();
        if(key1.equals(""))
            return orderDao.findByTimeBefore(endTime);
        if(key2.equals(""))
            return orderDao.findByTimeAfter(startTime);
        return orderDao.findByTimeBetween(startTime,endTime);
    }
}
