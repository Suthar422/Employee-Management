package com.bookseat.notification.controller;

import com.bookseat.notification.dto.SeatBooking;
import com.bookseat.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

//    @GetMapping("/notification/sms")
    @RabbitListener(queues = "${rabbitmq.queue.sms}")
    public void sendSMSNotification(SeatBooking seatBooking) {
        log.info("Received seat booking sms request: {}", seatBooking.toString());
        notificationService.sendNotificationSMS(seatBooking);
    }

//    @GetMapping("/notification/email")
    @RabbitListener(queues = "${rabbitmq.queue.email}")
    public void sendEmailNotification(SeatBooking seatBooking) {
        log.info("Received seat booking email request: {}", seatBooking.toString());
        notificationService.sendNotificationEmail(seatBooking);
    }



    @KafkaListener(topics = "${kafka.topic.email}", groupId = "${kafka.group}")
    public void sendKafkaNotification(SeatBooking seatBooking) {
        log.info("Received seat booking kafka request: {}", seatBooking.toString());
        notificationService.sendNotificationKafka(seatBooking);
    }
}
