package com.amigoscode.customers.controller;

import com.amigoscode.customers.model.Customer;
import com.amigoscode.customers.request.CustomerRegistrationRequest;
import com.amigoscode.customers.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public record CustomerController(CustomerService customerService) {

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest, @RequestHeader(value = "Authorization", required = false) String authorizationToken) {
        log.info("new customer registration {}", customerRegistrationRequest);
        customerService.registerCustomer(customerRegistrationRequest, authorizationToken);
    }

    @GetMapping
    public ResponseEntity getAllCustomers() {
        log.info("Getting all customers data");
        List<Customer> customerList = customerService.getAllCustomers();
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/findById")
    public ResponseEntity getCustomerById(@RequestParam("customer_id") Integer customerId) {
        log.info("Getting customer data with id {}", customerId);
        Customer customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }
}
