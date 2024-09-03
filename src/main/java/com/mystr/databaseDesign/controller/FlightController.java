package com.mystr.databaseDesign.controller;

import com.mystr.databaseDesign.Entities.Airline;
import com.mystr.databaseDesign.Entities.Flight;
import com.mystr.databaseDesign.Entities.Plane;

import com.mystr.databaseDesign.Result.Result;
import com.mystr.databaseDesign.Result.ResultFactory;
import com.mystr.databaseDesign.service.FlightService;
import com.mystr.databaseDesign.utils.StringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FlightController {
    @Autowired
    FlightService flightService;

    @CrossOrigin
    @GetMapping("/api/flights")
    public List<Flight> list() throws Exception{
        return flightService.getAll();
    }

    @CrossOrigin
    @PostMapping("/api/flights")
    @ResponseBody
    public Result addOrUpdate(@RequestBody Flight flight) {
        try {
            flightService.addOrUpdate(flight);
            return ResultFactory.buildSuccessResult(flight);
        }
        catch (Exception e){
            return ResultFactory.buildFailResult("编号已被使用");
        }
    }

    @CrossOrigin
    @PostMapping("/api/delete/flights")
    @ResponseBody
    public Result delete(@RequestBody Flight flight) throws Exception{
        flightService.deleteByFlightId(flight.getFlightId());
        return ResultFactory.buildSuccessResult(flight);
    }




    @CrossOrigin
    @GetMapping("/api/search/flights")
    public List<Flight> listFilter(@RequestParam("stateCodes") String stateCodes, @RequestParam("keywords") String keywords) throws Exception {
        List<Flight> resultList = new ArrayList<Flight>();
        if (stateCodes!=null && !stateCodes.equals("")) {
            int[] codes = StringFactory.StringSplitter(stateCodes);
            for(int code : codes){
                List<Flight> addList = flightService.getByState(code);
                resultList.removeAll(addList);
                resultList.addAll(addList);
            }
            if (keywords != null && !keywords.equals("")) {
                resultList.retainAll(flightService.search(keywords));
            }
        }
        else{
            if (keywords != null && !keywords.equals(""))
                resultList.addAll(flightService.search(keywords));
            else
                return list();
        }
        return  resultList;
    }

    @CrossOrigin
    @GetMapping("/api/search/ticket")
    public List<Flight> listFilterTicket(@RequestParam("stateCodes") String stateCodes, @RequestParam("checked") boolean checked, @RequestParam("keywords1") String keywords1, @RequestParam("keywords2") String keywords2, @RequestParam("keywords3") String keywords3, @RequestParam("date") String date) throws Exception {
        List<Flight> resultList = new ArrayList<Flight>();
        List<Flight> addList;
        if (stateCodes!=null && !stateCodes.equals("")) {
            int[] codes = StringFactory.StringSplitter(stateCodes);
            for (int code : codes) {
                addList = flightService.getByState(code);
                resultList.addAll(addList);
            }
        }
        else {
            resultList = flightService.getAll();
        }
        if (keywords1!=null && !keywords1.equals("")){
            addList = flightService.getByFlightNum(keywords1);
            resultList.retainAll(addList);
        }
        if (keywords2!=null && !keywords2.equals("")){
            addList = flightService.getByDeparture(keywords2);
            resultList.retainAll(addList);
        }
        if (keywords3!=null && !keywords3.equals("")){
            addList = flightService.getByDestination(keywords3);
            resultList.retainAll(addList);
        }
        if (date!=null && !date.equals("")){
            Date date1 = Date.valueOf(date);
            addList = flightService.getByFlightDate(date1);
            resultList.retainAll(addList);
        }
        if (!checked)
            resultList.removeAll(flightService.getFull());
        return  resultList;
    }
}

