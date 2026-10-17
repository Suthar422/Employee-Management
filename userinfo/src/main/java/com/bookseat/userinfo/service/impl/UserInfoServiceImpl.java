package com.bookseat.userinfo.service.impl;

import com.bookseat.userinfo.client.BookseatClient;
import com.bookseat.userinfo.dto.BookingResponse;
import com.bookseat.userinfo.dto.UserResponse;
import com.bookseat.userinfo.service.UserInfoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Executor;

@Service
@Slf4j

public class UserInfoServiceImpl implements UserInfoService {

    private final RestTemplate restTemplate;
    //
    private final RestClient restClient;
    private final Executor taskExecutor;

    private final WebClient webClient;

    //Feign Client
    private final BookseatClient bookseatClient;

//a core interface provided by Spring Cloud Commons that allows an application to interact with a Service Registry (like Netflix Eureka, Consul, or Zookeeper)
// //to discover the network locations (IP address and port) of other registered microservices
    private final DiscoveryClient discoveryClient;

 //   String url = "http://localhost:7080/api/v1/bookings";

    public UserInfoServiceImpl(RestTemplate restTemplate, RestClient restClient, Executor taskExecutor, WebClient webClient, BookseatClient bookseatClient, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.restClient = restClient;
        this.taskExecutor = taskExecutor;
        this.webClient = webClient;
        this.bookseatClient = bookseatClient;
        this.discoveryClient = discoveryClient;
    }



    @CircuitBreaker(name = "userinfoCircuitBreaker", fallbackMethod = "userinfoFallbackMethod")
    @Override
    public UserResponse getUserInfoById(Long userId) {

        //discovering service dynamically using discoveryClient
//        ServiceInstance bookseatService = discoveryClient.getInstances("BOOK-SEAT-SERVICE").get(0);
//        log.info("bookseatService: {}", bookseatService);
//        String url = bookseatService.getUri().toString()+"/bookseat";
//        log.info("url: {}", url);




        //hardcoding service url
//        String url = "http://localhost:7080/api/v1/bookings";

        log.info("Getting user info for userId: {}", userId);
   //     System.out.println("============================================" + url + "/" + userId);

        //using RestTemplate synchronous communication
        //  BookingResponse bookingResponse = restTemplate.getForObject(url+"/"+userId, BookingResponse.class);


        //using RestClient synchronous communication
//        BookingResponse bookingResponse = restClient.get()
//                .uri(url + "/" + userId)
//                .retrieve()
//                .toEntity(BookingResponse.class)
//                .getBody();

        //       BookingResponse bookingResponse = bookingClient.getBookingInfoById(userId);


        //using WebClient synchronous communication
//        BookingResponse bookingResponse = WebClient.create().get()
//                .uri(url + "/" + userId)
//                .retrieve()
//                .bodyToMono(BookingResponse.class)
//                .block();


        //using Feign Client (this is just declarative way of calling a service)
        log.info("Calling Book-seat Service using Feign Client");
        BookingResponse bookingResponse = bookseatClient.getBookingInfoById(userId);


        return UserResponse.builder()
                .username("John Doe")
                .email("john.doe@example.com")
                .bookingResponse(bookingResponse)
                .build();
    }


    @Async("taskExecutor")
    public void Even() throws InterruptedException {
        Thread.sleep(2000);
        log.info("Even from UserInfoServiceImpl" + " " + Thread.currentThread().getName());
    }

    @Async("taskExecutor")
    public void Odd() throws InterruptedException {
        Thread.sleep(2000);
        log.info("Odd from UserInfoServiceImpl" + " " + Thread.currentThread().getName());
    }

// when we try to get getUserInfoById, this required bookseat service to be available.
    // And if it is not available, then the fallback method will be executed. (Only if an exception occurs)
    public UserResponse userinfoFallbackMethod(Exception ex){
        log.info("Booking service in an unavailable..."+ex);
        log.info("userinfo fallback method executing...");
        return UserResponse.builder()
                .username("John Doe")
                .email("john.doe@example.com")
                .build();
    }

}
