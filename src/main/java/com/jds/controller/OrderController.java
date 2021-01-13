package com.jds.controller;

import com.jds.dao.entity.DoorsОrder;
import com.jds.dao.entity.UserEntity;
import com.jds.model.BackResponse.OrderResponse;
import com.jds.model.modelEnum.OrderStatus;
import com.jds.service.OrderService;
import com.jds.service.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class OrderController {


    @Autowired
    private UserServ userService;
    @Autowired
    private OrderService orderService;


    @GetMapping(value = "/orders")
    public String getOrdersPage(Model model,
                                @RequestParam(required = false, defaultValue = "0") String userId) throws Exception {

        List<DoorsОrder> list;
        boolean report = false;
        if (!"0".equals(userId)) {
            list = orderService.getOrders(userId);
            report = true;
        } else {
            list = orderService.getOrders();
        }

        model.addAttribute("report", report);
        model.addAttribute("accountInfos", list);
        model.addAttribute("isAdnin", userService.getCurrentUser().isAdmin());

        return "orders";
    }

    @GetMapping(value = "/order")
    public String getOrderPage(Model model, @RequestParam(required = false) String orderId) throws Exception {

        UserEntity user = userService.getCurrentUser();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("orderId", (orderId == null) ? 0 : orderId);
        return "order";
    }

    @GetMapping(value = "/getOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DoorsОrder getOrder(@RequestParam(required = false) String orderId) throws Exception {

        return orderService.getOrder(orderId);
    }

    @PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OrderResponse saveOrder(@RequestBody DoorsОrder order) throws Exception {

        return orderService.checkAccessAndSave(order);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteOrder(@RequestParam(required = false) String orderId) throws Exception {

        return orderService.deleteOrder(orderId);
    }

    @Secured("ROLE_ONE_C")
    @PostMapping(value = "/loading/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DoorsОrder> getOrders() throws Exception {

        return orderService.getOrders(OrderStatus.TO_WORK);
    }

    @Secured("ROLE_ONE_C")
    @PostMapping(value = "/order/status")
    public void setOrdersStatus(@RequestParam(required = false) String orderId,
                                @RequestParam(required = false) String status,
                                @RequestParam(required = false) String releasDate) throws Exception {

        orderService.setStatusAndSaveOrder(Integer.parseInt(orderId), status, new SimpleDateFormat("yyyy-MM-dd").parse(releasDate));

    }

}
