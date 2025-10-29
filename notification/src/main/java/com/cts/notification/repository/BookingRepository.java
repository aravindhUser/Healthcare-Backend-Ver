package com.cts.notification.repository;

import com.cts.notification.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByConfirmedTrueAndReminderSentFalse();
}
