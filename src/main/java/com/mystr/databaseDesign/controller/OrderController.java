package com.mystr.databaseDesign.controller;

import com.mystr.databaseDesign.Entities.Order;
import com.mystr.databaseDesign.Result.Result;

import com.mystr.databaseDesign.Result.ResultFactory;
import com.mystr.databaseDesign.service.OrderService;
import com.mystr.databaseDesign.utils.StringFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @CrossOrigin
    @GetMapping("/api/orders")
    public List<Order> list() throws Exception{
        return orderService.getAll();
    }

    @CrossOrigin
    @GetMapping("/api/myOrders")
    public List<Order> listMy() {
        return orderService.getMy();
    }


    @CrossOrigin
    @PostMapping("/api/orders")
    @ResponseBody
    public Result addOrUpdate(@RequestBody Order order) throws Exception{
        try {
            orderService.addOrUpdate(order);
            return ResultFactory.buildSuccessResult(order);
        }
        catch (IllegalAccessException e){
            return ResultFactory.buildFailResult("不能重复购票");
        }
        catch (UnsupportedOperationException e){
            return ResultFactory.buildResult(402,"不在允许时间内",order);
        }
    }

    @CrossOrigin
    @PostMapping("/api/delete/orders")
    public void delete(@RequestBody Order order) throws Exception{
        orderService.deleteByOrderId(order.getOrderId());
    }


    @CrossOrigin
    @PostMapping("/api/cancel/orders")
    @ResponseBody
    public Result cancel(@RequestBody Order order) throws Exception{
        try {
            orderService.cancelByOrderId(order.getOrderId());
            return ResultFactory.buildSuccessResult(order);
        }
        catch (Exception e){
            return ResultFactory.buildFailResult("不在允许时间内");
        }
    }


    @CrossOrigin
    @GetMapping("/api/search/orders")
    public List<Order> listFilter(@RequestParam("stateCodes") String stateCodes, @RequestParam("keywords") String keywords) throws Exception {
        List<Order> resultList = new ArrayList<Order>();
        if (stateCodes!=null && !stateCodes.equals("")) {
            int[] codes = StringFactory.StringSplitter(stateCodes);
            for(int code : codes){
                List<Order> addList = orderService.getByState(code);
                resultList.addAll(addList);
            }
            if (keywords != null && !keywords.equals("")) {
                resultList.retainAll(orderService.search(keywords));
            }
        }
        else{
            if (keywords != null && !keywords.equals(""))
                resultList.addAll(orderService.search(keywords));
            else
                return list();
        }
        return  resultList;
    }

    @CrossOrigin
    @GetMapping("/api/search/myOrders")
    public List<Order> listFilterMy(@RequestParam("stateCodes") String stateCodes) {
        List<Order> resultList = new ArrayList<Order>();
        if (stateCodes!=null && !stateCodes.equals("")) {
            int[] codes = StringFactory.StringSplitter(stateCodes);
            for(int code : codes){
                List<Order> addList = orderService.getByState(code);
                resultList.addAll(addList);
            }
        }
        resultList.retainAll(orderService.getMy());
        return  resultList;
    }
}
