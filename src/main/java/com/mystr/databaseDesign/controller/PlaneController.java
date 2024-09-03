package com.mystr.databaseDesign.controller;

import com.mystr.databaseDesign.Entities.Plane;
import com.mystr.databaseDesign.Result.Result;
import com.mystr.databaseDesign.Result.ResultFactory;
import com.mystr.databaseDesign.service.PlaneService;
import com.mystr.databaseDesign.utils.StringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class PlaneController {
    @Autowired
    PlaneService planeService;

    @CrossOrigin
    @GetMapping("/api/planes")
    public List<Plane> list() throws Exception{
        return planeService.getAll();
    }

    @CrossOrigin
    @PostMapping("/api/planes")
    @ResponseBody
    public Result addOrUpdate(@RequestBody Plane plane) {
        try {
            planeService.addOrUpdate(plane);
            return ResultFactory.buildSuccessResult(plane);
        }
        catch (Exception e){
            return ResultFactory.buildFailResult("编号已被使用");
        }
    }

    @CrossOrigin
    @PostMapping("/api/delete/planes")
    @ResponseBody
    public Result delete(@RequestBody Plane plane) throws Exception{
        try {
            planeService.deleteByPlaneId(plane.getPlaneId());
            return ResultFactory.buildSuccessResult(plane);
        }
        catch (IllegalAccessException e){
            return ResultFactory.buildFailResult("该飞机存在未删除的航线");
        }
    }




    @CrossOrigin
    @GetMapping("/api/search/planes")
    public List<Plane> listFilter(@RequestParam("stateCodes") String stateCodes, @RequestParam("keywords") String keywords) throws Exception {
        List<Plane> resultList = new ArrayList<Plane>();
        if (stateCodes!=null && !stateCodes.equals("")) {
            int[] codes = StringFactory.StringSplitter(stateCodes);
            for(int code : codes){
                List<Plane> addList = planeService.getByState(code);
                resultList.addAll(addList);
            }
            if (keywords != null && !keywords.equals("")) {
                resultList.retainAll(planeService.search(keywords));
            }
        }
        else{
            if (keywords != null && !keywords.equals(""))
                resultList.addAll(planeService.search(keywords));
            else
                return list();
        }
        return  resultList;
    }

    @CrossOrigin
    @GetMapping("/api/capacity/{startValue}/{endValue}/planes")
    public List<Plane> listByCapacityRange(@PathVariable("startValue") int startValue, @PathVariable("endValue") int endValue ) throws Exception {
        if (endValue > 0 && endValue >= startValue) {
            return planeService.getByCapacity(startValue,endValue);
        } else {
            return null;
        }
    }
}

