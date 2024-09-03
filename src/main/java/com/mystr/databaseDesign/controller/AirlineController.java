package com.mystr.databaseDesign.controller;

import com.mystr.databaseDesign.Entities.Airline;
import com.mystr.databaseDesign.Entities.Plane;

import com.mystr.databaseDesign.Result.Result;
import com.mystr.databaseDesign.Result.ResultFactory;
import com.mystr.databaseDesign.service.AirlineService;
import com.mystr.databaseDesign.utils.StringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AirlineController {
    @Autowired
    AirlineService airlineService;

    @CrossOrigin
    @GetMapping("/api/airlines")
    public List<Airline> list() throws Exception{
        return airlineService.getAll();
    }

    @CrossOrigin
    @PostMapping("/api/airlines")
    @ResponseBody
    public Result addOrUpdate(@RequestBody Airline airline) {
        try {
            airlineService.addOrUpdate(airline);
            return ResultFactory.buildSuccessResult(airline);
        }
        catch (Exception e){
            return ResultFactory.buildFailResult("编号已被使用");
        }
    }

    @CrossOrigin
    @PostMapping("/api/delete/airlines")
    @ResponseBody
    public Result delete(@RequestBody Airline airline) throws Exception{
        try {
            airlineService.deleteByAirlineId(airline.getAirlineId());
            return ResultFactory.buildSuccessResult(airline);
        }
        catch (IllegalAccessException e){
            return ResultFactory.buildFailResult("该航线存在未删除的航班");
        }
    }




    @CrossOrigin
    @GetMapping("/api/search/airlines")
    public List<Airline> listFilter(@RequestParam("activeCodes") String activeCodes, @RequestParam("keywords") String keywords) throws Exception {
        List<Airline> resultList = new ArrayList<Airline>();
        if (activeCodes!=null && !activeCodes.equals("")) {
            int[] codes = StringFactory.StringSplitter(activeCodes);
            for(int code : codes){
                List<Airline> addList = airlineService.getByActive(code);
                resultList.addAll(addList);
            }
            if (keywords != null && !keywords.equals("")) {
                resultList.retainAll(airlineService.search(keywords));
            }
        }
        else{
            if (keywords != null && !keywords.equals(""))
                resultList.addAll(airlineService.search(keywords));
            else
                return list();
        }
        return  resultList;
    }

    @CrossOrigin
    @GetMapping("/api/price/{startValue}/{endValue}/planes")
    public List<Airline> listByPriceRange(@PathVariable("startValue") int startValue, @PathVariable("endValue") int endValue ) throws Exception {
        if (endValue > 0 && endValue >= startValue) {
            return airlineService.getByYPriceBetween(startValue,endValue);
        } else {
            return null;
        }
    }
}

