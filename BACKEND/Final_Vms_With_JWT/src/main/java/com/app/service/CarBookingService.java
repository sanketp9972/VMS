package com.app.service;

import com.app.dto.CarBookingDTO;
import com.app.dto.CarBookingPaymentStatusDTO;
import com.app.entity.CarBooking;
import com.app.entity.PaymentStatus;

import java.util.List;

public interface CarBookingService {
    CarBooking addBooking(CarBookingDTO bookingDTO);
    List<CarBooking> getBookingsByUserId(Long userId);
    List<CarBooking> getBookingsByCarId(Long carId);
    List<CarBooking> getAllBookings();
    CarBooking updateBooking(Long bookingId, CarBookingDTO bookingDTO);
    void deleteBooking(Long bookingId);
    CarBooking getBookingById(Long bookingId);
    CarBooking updateStatus(Long bookingId, CarBookingPaymentStatusDTO payDTO);
    
 // New method to send booking confirmation email
    void sendBookingConfirmationEmail(String recipientEmail, String carModel, String user , PaymentStatus status);
}
