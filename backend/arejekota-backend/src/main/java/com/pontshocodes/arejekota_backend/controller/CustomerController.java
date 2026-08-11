package com.pontshocodes.arejekota_backend.controller;


import com.pontshocodes.arejekota_backend.entity.Customer;
import com.pontshocodes.arejekota_backend.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    //Creating my first end point
    @PostMapping("/register")
    public Customer registerCustomer(){

    }
}
