package com.bookseat.notification.service.impl;

import com.bookseat.notification.dto.SeatBooking;
import com.bookseat.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotificationSMS(SeatBooking seatBooking) {
        log.info("Sending sms notification for seat booking: {}", seatBooking.toString());
        log.info("SMS sent successfully. seatId: {}, date: {}", seatBooking.getSeatId(), seatBooking.getBookingDate());
    }


    @Override
    public void sendNotificationEmail(SeatBooking seatBooking) {
        log.info("Sending email notification for seat booking: {}", seatBooking.toString());
        log.info("Email sent successfully. seatId: {} , date: {}", seatBooking.getSeatId() , seatBooking.getBookingDate());
    }

    @Override
    public void sendNotificationKafka(SeatBooking seatBooking) {
        log.info("Sending email notification from Kafka topic for seat booking: {}", seatBooking.toString());
        log.info("Email sent successfully. seatId: {} , date: {}", seatBooking.getSeatId() , seatBooking.getBookingDate());
    }

}
