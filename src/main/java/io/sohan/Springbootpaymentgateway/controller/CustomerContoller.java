package io.sohan.Springbootpaymentgateway.controller;

import com.google.gson.Gson;
import io.sohan.Springbootpaymentgateway.dto.request.CustomerRequestDto;
import io.sohan.Springbootpaymentgateway.dto.response.CustomerResponseDto;
import io.sohan.Springbootpaymentgateway.service.CustomerServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("customers")
public class CustomerContoller {
    private final Gson gson = new Gson();
    private CustomerServices customerServices;

    public CustomerContoller(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }


    @GetMapping("{id}")
    @ResponseBody
    public CustomerResponseDto getById(@PathVariable long id) {
        return customerServices.getById(id);
    }

    @PostMapping("/create")
    @ResponseBody
    public String create(@RequestBody CustomerRequestDto customerRequestDto) {
        System.out.println(customerRequestDto);
        customerServices.add(customerRequestDto);
        return gson.toJson(customerRequestDto);
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public String updateCustomerById(@PathVariable Long id, @RequestBody CustomerRequestDto customerRequestDto) {
        System.out.println(customerRequestDto);
        customerServices.update(id, customerRequestDto);
        return gson.toJson(customerRequestDto);
    }

    @RequestMapping(value = "update/{id}/show")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("customers", customerServices.getById(id));
        return "customer/customerUpdate";
    }

    @RequestMapping(value = "/show")
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", customerServices.getAll());
        return "customer/customerShow";
    }

    @RequestMapping(value = "{id}/show")
    public String getCustomerById(@PathVariable Long id, Model model) {
        model.addAttribute("customers", customerServices.getById(id));
        return "cart_checkout";
    }


}
