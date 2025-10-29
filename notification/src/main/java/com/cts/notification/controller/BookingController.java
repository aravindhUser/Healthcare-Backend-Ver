package com.cts.notification.controller;

import com.cts.notification.model.Booking;
import com.cts.notification.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/confirm")
    public ResponseEntity<Booking> confirmBooking(@RequestBody Booking booking) {
        booking.setConfirmed(true);
        booking.setReminderSent(false);
        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/reminders")
    public List<Booking> getPendingReminders() {
        return bookingRepository.findByConfirmedTrueAndReminderSentFalse();
    }

    @PostMapping("/reminder-sent/{id}")
    public ResponseEntity<Void> markReminderSent(@PathVariable Integer id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setReminderSent(true);
        bookingRepository.save(booking);
        return ResponseEntity.ok().build();
    }
}