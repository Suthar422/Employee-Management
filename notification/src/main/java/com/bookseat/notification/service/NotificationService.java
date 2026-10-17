package com.bookseat.notification.service;

import com.bookseat.notification.dto.SeatBooking;

public interface NotificationService {

     void sendNotificationSMS(SeatBooking seatBooking);
     void sendNotificationEmail(SeatBooking seatBooking);

    void sendNotificationKafka(SeatBooking seatBooking);
}
