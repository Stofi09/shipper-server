package com.shipper.server.controller;

import com.shipper.server.dto.CustomerRequest;
import com.shipper.server.dto.CustomerResponse;
import com.shipper.server.dto.ProductRequest;
import com.shipper.server.dto.ProductResponse;
import com.shipper.server.service.CustomerService;
import com.shipper.server.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@RequestBody CustomerRequest customerRequest){
        service.createProduct(customerRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllProducts(){
        return service.findAll();
    }
}
