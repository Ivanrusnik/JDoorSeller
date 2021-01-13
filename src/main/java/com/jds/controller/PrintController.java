package com.jds.controller;

import com.jds.model.orderPrint.OrderPrint;
import com.jds.model.PrintAppToTheOrder;
import com.jds.service.OrderDiscountService;
import com.jds.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PrintController {

    @Autowired
    private OrderService service;
    @Autowired
    private OrderDiscountService orderDiscountService;

    @GetMapping(value = "/orderprint")
    public String getPrintOrder(Model model,
                                @RequestParam(required = false) String orderId) throws Exception {

        model.addAttribute("order", new OrderPrint(service.getOrder(orderId), orderDiscountService.getOrderDiscounts(orderId)));
        return "orderPrint";
    }
    @GetMapping(value = "/doorsprint")
    public String getPrintDoors(Model model,
                                @RequestParam(required = false) String orderId) throws Exception {

        model.addAttribute("orderId",orderId);
        return "doorsPrint";
    }

    @GetMapping(value = "/getPrintApp", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<PrintAppToTheOrder> getPrintAppToTheOrder(Model model,
                                                          @RequestParam String orderId) throws Exception {

        return service.getPrintAppList(orderId);
    }

}
