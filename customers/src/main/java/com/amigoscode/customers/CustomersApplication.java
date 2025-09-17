package com.amigoscode.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(scanBasePackages = {"com.amigoscode.customers", "com.amigoscode.amqp", "com.amigoscode.common"})
@EnableEurekaClient
//@EnableFeignClients(basePackageClasses = {FraudApplication.class}, basePackages = "com.amigoscode.clients")
//@EnableFeignClients(basePackages = "com.amigoscode.clients")
@EnableFeignClients(basePackages = {"com.amigoscode.clients.fraud", "com.amigoscode.clients.notification"})
//@ImportAutoConfiguration({FeignAutoConfiguration.class})
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class CustomersApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }
}
