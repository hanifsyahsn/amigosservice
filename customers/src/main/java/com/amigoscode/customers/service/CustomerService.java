package com.amigoscode.customers.service;

import com.amigoscode.amqp.RabbitMQMessageProducer;
import com.amigoscode.clients.fraud.FraudDescriptor;
import com.amigoscode.clients.fraud.response.FraudCheckHistoryResponse;
import com.amigoscode.clients.notification.NotificationDescriptor;
import com.amigoscode.clients.notification.request.NotificationRequest;
import com.amigoscode.common.util.jwt.JwtUtil;
import com.amigoscode.customers.model.Customer;
import com.amigoscode.customers.repository.CustomerRepository;
import com.amigoscode.customers.request.CustomerRegistrationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.util.List;

@Service
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate,
                              FraudDescriptor fraudDescriptor,
                              RabbitMQMessageProducer rabbitMQMessageProducer, JwtUtil jwtUtil) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest, String authoriationToken) {
        jwtUtil.verify(authoriationToken);
        Customer customer = Customer.builder().firstName(customerRegistrationRequest.firstName()).lastName(customerRegistrationRequest.lastName()).email(customerRegistrationRequest.email()).build();
        customerRepository.saveAndFlush(customer);
        FraudCheckHistoryResponse fraudCheckHistoryResponse = fraudDescriptor.isFraudster(customer.getId());
        if (fraudCheckHistoryResponse.isFraudster()) {
            throw new RuntimeException("Fraudster");
        }
        NotificationRequest notificationRequest = NotificationRequest
                .builder()
                .customerId(customer.getId())
                .customerEmail(customer.getEmail())
                .message(String.format("Hi %s, welcome to the jungle", customer.getFirstName()))
                .build();
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer is not found"));
    }
}
