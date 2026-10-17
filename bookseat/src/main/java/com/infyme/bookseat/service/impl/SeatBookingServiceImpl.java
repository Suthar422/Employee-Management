package com.infyme.bookseat.service.impl;

import com.infyme.bookseat.config.RabbitMQConfig;
import com.infyme.bookseat.dto.BookingRequest;
import com.infyme.bookseat.dto.BookingResponse;
import com.infyme.bookseat.entity.SeatBooking;
import com.infyme.bookseat.repository.SeatBookingRepository;
import com.infyme.bookseat.service.SeatBookingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class SeatBookingServiceImpl implements SeatBookingService {

    Logger logger = LoggerFactory.getLogger(SeatBookingServiceImpl.class);
    @Value("${rabbitmq.exchange.name}")
    public String EXCHANGE_NAME;

    @Value("${rabbitmq.queue.email}")
    private String EMAIL_QUEUE;
    @Value("${rabbitmq.queue.sms}")
    private String SMS_QUEUE;
    @Value("${rabbitmq.routing-key.email}")
    public String EMAIL_KEY;
    @Value("${rabbitmq.routing-key.sms}")
    public String SMS_KEY;

    private final SeatBookingRepository seatBookingRepository;

    //rabbitMQ
    private final RabbitTemplate rabbitTemplate;

    private final KafkaTemplate<String, SeatBooking> kafkaTemplate;

    public SeatBookingServiceImpl(SeatBookingRepository seatBookingRepository, RabbitTemplate rabbitTemplate, KafkaTemplate<String, SeatBooking> kafkaTemplate) {
        this.seatBookingRepository = seatBookingRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public BookingResponse bookSeat(BookingRequest bookingRequest, Long userId) {

        log.info("Booking seat for user: {}", userId);
        SeatBooking seatBooking = SeatBooking.builder()
                .userId(userId)
                .bookingDate(bookingRequest.getBookingDate())
                .bookingAt(LocalDateTime.now())
                .seatId(bookingRequest.getSeatId())
                .status("BOOKED")
                .build();

        SeatBooking saved = seatBookingRepository.save(seatBooking);


        //sending a notification to the user -- asynchronous
        log.info("Seat booked successfully for user: {}", userId);
        System.out.println("============================================"+EXCHANGE_NAME +EMAIL_KEY+ EMAIL_QUEUE);
        log.info("Sending message to RabbitMQ. I'm a producer");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, EMAIL_KEY, seatBooking);
        log.info("Message sent to RabbitMQ EMAIL Queue successfully.");

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, SMS_KEY, seatBooking);
        log.info("Message sent to RabbitMQ SMS Queue successfully.");

        //sending a notification to the user -- asynchronous
        //topic – the topic.
        //data – The data
        kafkaTemplate.send("email-topic", seatBooking);
        log.info("Message sent to Kafka EMAIL Topic successfully.");
//        kafkaTemplate.send("${kafka.topic.sms}", seatBooking);
//        log.info("Message sent to Kafka SMS Topic successfully.");

        return BookingResponse.builder()
                .bookingId(saved.getBookingId())
                .userId(userId)
                .seatId(bookingRequest.getSeatId())
                .message("Seat Booked Successfully")
                .build();
    }

    @Override
    @Cacheable(value = "seatInfoCache", key = "#userId")
    public BookingResponse getBookingInfoForUser(Long userId) {
        //find booking info for a user existing in the database
        SeatBooking seat  = seatBookingRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Booking not found for user: " + userId));
        return BookingResponse.builder()
                .bookingId(seat.getBookingId())
                .userId(seat.getUserId())
                .seatId(seat.getSeatId())
                .message("Booking found successfully")
                .build();
    }

    @Override
    @Transactional
    //it will first delete the database and then delete the cache as well
    //cache stores data in concurrent hashmap, and if we not pass key then it will store in default key
    @CacheEvict(value = "seatInfoCache", key = "#userId")
    public void deleteBookingInfoForUser(Long userId) {
        log.info("Deleting booking info for user: {}", userId);
        seatBookingRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    //it will first update the database and then update the cache as well
    //cache stores data in concurrent hashmap, and if we not pass key then it will store in default key
    //every time it stores in new key will create an inconsistency
    //make sure to pass key explicitly, so it will update the same entry in the cache
    @CachePut(value = "seatInfoCache", key = "#userId")
    public void updateBookingInfoForUser(Long userId, String seatId) {
        //find details of the user and update the booking info
        SeatBooking seat = seatBookingRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Booking not found for user: " + userId));
        seat.setSeatId(seatId);
        seat.setStatus("UPDATED");
        seatBookingRepository.save(seat);
    }
}
