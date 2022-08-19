package io.sohan.Springbootpaymentgateway.controller;

import com.google.gson.Gson;
import com.razorpay.RazorpayException;
import io.sohan.Springbootpaymentgateway.dto.request.OrderCheckDto;
import io.sohan.Springbootpaymentgateway.dto.request.OrderRequestDto;
import io.sohan.Springbootpaymentgateway.service.OrderServices;
import io.sohan.Springbootpaymentgateway.service.PaymentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class PaymentGateway {
    private static final Gson gson = new Gson();
    private final OrderServices orderService;
    private final PaymentServices paymentServices;

    public PaymentGateway(OrderServices orderService, PaymentServices paymentServices) {
        this.orderService = orderService;
        this.paymentServices = paymentServices;
    }


    @PostMapping(value = "/createPayment")
    @ResponseBody
    public ResponseEntity<String> checkout(@RequestBody OrderRequestDto orderRequestDto) {

        return new ResponseEntity<>(gson.toJson(orderService.add(orderRequestDto)), HttpStatus.OK);
    }


    //    @GetMapping("{id}")
//    @ResponseBody
//    public String getOrder(@PathVariable String id){
//        System.out.println(id);
//        Gson gson=new Gson();
//        return gson.toJson(orderService.getOrder(id));
//    }
//
//    @PostMapping(value = "/checkPayment")
//    @ResponseBody
//    public String checkOder(@RequestBody OrderCheckDto orderCheckDto, @RequestParam String id) throws RazorpayException {
//        Gson gson = new Gson();
//        System.out.println(id);
//        System.out.println(orderCheckDto);
//        paymentServices.add(id, orderCheckDto);
//        return gson.toJson(orderCheckDto);
//    }

    @PostMapping(value = "/checkPayment")
    @ResponseBody
    public String checkOder(@RequestBody OrderCheckDto orderCheckDto) throws RazorpayException {
        Gson gson = new Gson();
//        System.out.println(orderCheckDto);
        orderService.update(orderCheckDto);
        return gson.toJson(orderCheckDto);
    }

    @PostMapping(value = "/pay")
    @ResponseBody
    public String pay(@RequestBody OrderCheckDto orderCheckDto) throws RazorpayException {
        Gson gson = new Gson();
//        System.out.println(orderCheckDto);
        paymentServices.add(orderCheckDto);
        return gson.toJson(orderCheckDto);
    }

    @GetMapping(value = "/pay/pending")
    @ResponseBody
    public String pending(@RequestParam String id) {

        return gson.toJson(orderService.pending(id));
    }

    @RequestMapping(value = "/model/{id}/refund")
    public String getrefund(@PathVariable String id, Model model) throws RazorpayException {
        model.addAttribute("payments", paymentServices.getById(id));
        return "refund";
    }

    @RequestMapping(value = "/update/payment")
    @ResponseBody
    public String updatePayment(@RequestParam String id) throws RazorpayException {

//        return gson.toJson(id);
        return gson.toJson(paymentServices.update(id));
    }
}
