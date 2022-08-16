package io.sohan.Springbootpaymentgateway.controller;

import io.sohan.Springbootpaymentgateway.service.OrderServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("orders")
public class OrderController {
    private OrderServices orderServices;

    public OrderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

//    @GetMapping("{id}")
//    public List<OrderResponseDto> getAllOrder(@PathVariable long id) {
//        return orderServices.getAll(id);
//    }

    @RequestMapping(value = "{id}/show")
    public String getAllOrders(@PathVariable Long id, Model model) {
        model.addAttribute("orders", orderServices.getAll(id));
        return "order/orderShow";
    }

    @RequestMapping(value = "/model/{id}/pending")
    public String getPending(@PathVariable String id, Model model) {
        model.addAttribute("orders", orderServices.pending(id));
        return "pending";
    }


}
