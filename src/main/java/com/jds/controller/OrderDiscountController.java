package com.jds.controller;

import com.jds.dao.entity.DoorsОrder;
import com.jds.dao.entity.OrderDiscount;
import com.jds.model.BackResponse.OrderResponse;
import com.jds.model.ResponseAction;
import com.jds.model.modelEnum.OrderStatus;
import com.jds.service.OrderDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderDiscountController {
    @Autowired
    private OrderDiscountService orderDiscountService;

    @PostMapping(value = "/orderDiscount", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<OrderDiscount> saveOrderDiscount(@RequestBody List<OrderDiscount> orderDiscount) throws Exception {

        return orderDiscountService.saveOrderDiscount(orderDiscount);
    }

    @GetMapping(value = "/orderDiscounts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<OrderDiscount> getOrderDiscount(@RequestParam(required = false) String orderId) throws Exception {

        return orderDiscountService.getOrderDiscount(orderId);
    }

    @DeleteMapping(value = "/orderDiscount/{id}")
    @ResponseBody
    public ResponseAction deleteOrderDiscount(@PathVariable String id) {

        return new ResponseAction(orderDiscountService.deleteOrderDiscount(id));
    }
}
