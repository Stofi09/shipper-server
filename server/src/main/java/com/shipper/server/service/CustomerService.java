package com.shipper.server.service;

import com.shipper.server.dto.CustomerRequest;
import com.shipper.server.dto.CustomerResponse;
import com.shipper.server.dto.ProductRequest;
import com.shipper.server.dto.ProductResponse;
import com.shipper.server.model.Customer;
import com.shipper.server.model.Product;
import com.shipper.server.repository.CustomerRepository;
import com.shipper.server.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class CustomerService  {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }
    public void createProduct(CustomerRequest request) {
        Customer customer = Customer.builder()
                .name(request.getName())
                .build();
        repository.save(customer);
        log.info("Product is save: " + customer.toString());
    }

    public List<CustomerResponse> findAll() {
        List<Customer> customers = repository.findAll();
        return customers.stream().map(this:: mapToProductResponse).toList();
    }

    private CustomerResponse mapToProductResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .build();
    }
}
