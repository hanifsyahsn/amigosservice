package com.amigoscode.customers;

import com.amigoscode.clients.fraud.FraudDescriptor;
import com.amigoscode.clients.fraud.response.FraudCheckHistoryResponse;
import com.amigoscode.clients.notification.NotificationDescriptor;
import com.amigoscode.clients.notification.request.NotificationRequest;
import com.amigoscode.customers.model.Customer;
import com.amigoscode.customers.repository.CustomerRepository;
import com.amigoscode.customers.request.CustomerRegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerControllerTest {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CustomerRepository customerRepository;

    @MockBean
    FraudDescriptor fraudDescriptorMock;

    @MockBean
    NotificationDescriptor notificationDescriptorMock;

    @BeforeEach
    void nefrTable() {
        customerRepository.deleteAll();
    }

    FraudCheckHistoryResponse fraudCheckHistoryResponse = new FraudCheckHistoryResponse(false);
    CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest("Hanifsyah",
            "Solehuddin Nazir",
            "hanifsyahsn@gmail.com");

    @Test
    public void shouldCreateCustomer() throws Exception {
        String customerRegistration = objectMapper.writeValueAsString(customerRegistrationRequest);

        when(fraudDescriptorMock.isFraudster(any())).thenReturn(fraudCheckHistoryResponse);
        doNothing().when(notificationDescriptorMock).sendNotification(anyObject());

        mockMvc.perform(post("/api/v1/customers").contentType(MediaType.APPLICATION_JSON).content(customerRegistration)).andExpect(status().isOk());

        List<Customer> customerList = customerRepository.findAll();

        Assertions.assertEquals(1, customerList.size());
        Assertions.assertEquals(customerRegistrationRequest.firstName(), customerList.get(0).getFirstName());
        Assertions.assertEquals(customerRegistrationRequest.lastName(), customerList.get(0).getLastName());
        Assertions.assertEquals(customerRegistrationRequest.email(), customerList.get(0).getEmail());
    }

    @Test
    public void shoudGetCustomer() throws Exception {
        Customer customer = customerRepository.saveAndFlush(Customer.builder().firstName("Hanifsyah").lastName("Solehuddin Nazir").email("hanifsyahsn@gmail.com").build());

        mockMvc.perform(get("/api/v1/customers/findById").contentType(MediaType.APPLICATION_JSON)
                        .param("customer_id", customer.getId().toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.first_name", is(customer.getFirstName())))
                .andExpect(jsonPath("$.last_name", is(customer.getLastName())))
                .andExpect(jsonPath("$.email", is(customer.getEmail())));
    }

    @Test
    public void shouldGetCustomers() throws Exception {
        List<Customer> customerList = customerRepository.saveAll(
                List.of(Customer.builder().firstName("Hanifsyah").lastName("Solehuddin Nazir").email("hanifsyahsn@gmail.com").build(),
                        Customer.builder().firstName("Sri").lastName("Intan").email("sriintan@gmail.com").build()));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].first_name", is(customerList.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].last_name", is(customerList.get(0).getLastName())))
                .andExpect(jsonPath("$[0].email", is(customerList.get(0).getEmail())))
                .andExpect(jsonPath("$[1].first_name", is(customerList.get(1).getFirstName())))
                .andExpect(jsonPath("$[1].last_name", is(customerList.get(1).getLastName())))
                .andExpect(jsonPath("$[1].email", is(customerList.get(1).getEmail())));
    }
}
