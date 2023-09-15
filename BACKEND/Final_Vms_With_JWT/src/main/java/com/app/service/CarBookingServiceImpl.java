package com.app.service;

import com.app.dto.CarBookingDTO;

import com.app.dto.CarBookingPaymentStatusDTO;
import com.app.entity.Cars;
import com.app.entity.PaymentStatus;
import com.app.entity.CarBooking;
import com.app.entity.CarFinance;
import com.app.entity.CarInsurance;
import com.app.entity.Users;
import com.app.repository.CarBookingRepository;
import com.app.repository.CarFinanceRepository;
import com.app.repository.CarInsuranceRepository;
import com.app.repository.CarsRepository;
import com.app.repository.UsersRepository;
import com.app.service.CarBookingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;



@Service

public class CarBookingServiceImpl implements CarBookingService {

    @Autowired
    private CarBookingRepository carBookingRepository;

    @Autowired
    private CarsRepository carRepository;

    @Autowired
    private UsersRepository userRepository;
    
    @Autowired
    private CarInsuranceRepository carInsuranceRepository;

    @Autowired
    private CarFinanceRepository carFinanceRepository;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Override
    public CarBooking addBooking(CarBookingDTO bookingDTO) {
        Optional<Cars> carOptional = carRepository.findById(bookingDTO.getCarId());
        Optional<Users> userOptional = userRepository.findById(bookingDTO.getUserId());
        Optional<CarInsurance> insuranceOptional =carInsuranceRepository.findById(bookingDTO.getInsuranceId());
        Optional<CarFinance> financeOptional= carFinanceRepository.findById(bookingDTO.getFinanceId());

        if (carOptional.isPresent() && userOptional.isPresent()) {
            CarBooking booking = new CarBooking();
            booking.setBookingDate(LocalDate.now());
            booking.setDeliveryDate(Date.from(bookingDTO.getDeliveryDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            booking.setCar(carOptional.get());
            booking.setUser(userOptional.get());
            booking.setInsurance(insuranceOptional.get());
            booking.setFinance(financeOptional.get());
            booking.setStatus(bookingDTO.getStatus());
            
         // Send email confirmation
         sendBookingConfirmationEmail(booking.getUser().getEmail(), booking.getCar().getModelName(),booking.getUser().getFirstName(),bookingDTO.getStatus());
            return carBookingRepository.save(booking);
            
            
        }
        return null;
    }

    @Override
    public List<CarBooking> getBookingsByUserId(Long userId) {
        return carBookingRepository.findByUserId(userId);
    }

    @Override
    public List<CarBooking> getBookingsByCarId(Long carId) {
        return carBookingRepository.findByCarId(carId);
    }

    @Override
    public List<CarBooking> getAllBookings() {
        return carBookingRepository.findAll();
    }

    @Transactional
    @Override
    public CarBooking updateBooking(Long bookingId, CarBookingDTO bookingDTO) {
        Optional<CarBooking> bookingOptional = Optional.of(carBookingRepository.getById(bookingId));
        if (bookingOptional.isPresent()) {
            CarBooking booking = bookingOptional.get();
            booking.setBookingDate(LocalDate.now());
            booking.setStatus(bookingDTO.getStatus());
            booking.setDeliveryDate(Date.from(bookingDTO.getDeliveryDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            return carBookingRepository.saveAndFlush(booking);
            
        }
        return null;
    }

    public void sendBookingConfirmationEmail(String recipientEmail, String carModel , String user,PaymentStatus status) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject("Booking Confirmation");
            helper.setText(" Thank you " + user + " for booking the " + carModel + " car. Your booking is confirmed! " + "  Your Payment Status Is.. "+ status+ " If Status Is Pending You Need To Pay At Delivery ");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Handle email sending failure
            e.printStackTrace();
        }
    }
    
    @Override
    public void deleteBooking(Long bookingId) {
        carBookingRepository.deleteById(bookingId);
    }

    @Override
    public CarBooking getBookingById(Long bookingId) {
        return carBookingRepository.findById(bookingId).orElse(null);
    }

    @Transactional
	@Override
	public CarBooking updateStatus(Long bookingId, CarBookingPaymentStatusDTO payDTO) {
		Optional<CarBooking> bookingOptional = Optional.of(carBookingRepository.getById(bookingId));
		if(bookingOptional.isPresent()) {
			CarBooking booking = bookingOptional.get();
			booking.setStatus(payDTO.getStatus());
			return carBookingRepository.saveAndFlush(booking);
		}
		return null;
	}
    
    
}
