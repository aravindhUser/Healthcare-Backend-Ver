package com.cts.notification.service;

import com.cts.notification.exception.NotificationNotFoundException;
import com.cts.notification.exception.NotificationProcessingException;
import com.cts.notification.model.Notification;
import com.cts.notification.repo.NotificationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTests {

    @Mock
    private NotificationRepo notificationRepo;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private Notification sampleNotification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleNotification = new Notification();
        sampleNotification.setPatientId(1);
        sampleNotification.setDoctorId(2);
        sampleNotification.setAppointmentId(100);
        sampleNotification.setDate(LocalDate.of(2025, 11, 5));
        sampleNotification.setStartTime(LocalTime.of(10, 30));
        sampleNotification.setDoctorName("Strange");
        sampleNotification.setPatientName("John Doe");
    }

    // ✅ Success scenarios

    @Test
    void testNotifyBookedSuccess() {
        when(notificationRepo.save(any())).thenReturn(sampleNotification);

        Notification result = notificationService.notifyBooked(sampleNotification);

        assertNotNull(result.getTimestamp());
        assertTrue(result.getMessage().contains("appointment successfully booked"));
        verify(notificationRepo).save(sampleNotification);
    }

    @Test
    void testNotifyCancelledByPatientSuccess() {
        when(notificationRepo.save(any())).thenReturn(sampleNotification);

        Notification result = notificationService.notifyCancelledByPatient(sampleNotification);

        assertNotNull(result.getTimestamp());
        assertTrue(result.getMessage().contains("cancelled by Patient"));
        verify(notificationRepo).save(sampleNotification);
    }

    @Test
    void testNotifyCancelledByDoctorSuccess() {
        when(notificationRepo.save(any())).thenReturn(sampleNotification);

        Notification result = notificationService.notifyCancelledByDoctor(sampleNotification);

        assertNotNull(result.getTimestamp());
        assertTrue(result.getMessage().contains("cancelled by Doctor"));
        verify(notificationRepo).save(sampleNotification);
    }

    @Test
    void testGetNotificationsForPatientSuccess() {
        when(notificationRepo.findByPatientIdOrderByTimestampDesc(1)).thenReturn(List.of(sampleNotification));

        List<Notification> result = notificationService.getNotificationsForPatient(1);

        assertEquals(1, result.size());
        verify(notificationRepo).findByPatientIdOrderByTimestampDesc(1);
    }

    @Test
    void testGetNotificationsForDoctorSuccess() {
        when(notificationRepo.findByDoctorIdOrderByTimestampDesc(2)).thenReturn(List.of(sampleNotification));

        List<Notification> result = notificationService.getNotificationsForDoctor(2);

        assertEquals(1, result.size());
        verify(notificationRepo).findByDoctorIdOrderByTimestampDesc(2);
    }

    // ❌ Exception scenarios

    @Test
    void testNotifyBookedThrowsProcessingException() {
        when(notificationRepo.save(any())).thenThrow(new RuntimeException("DB error"));

        NotificationProcessingException ex = assertThrows(NotificationProcessingException.class, () ->
                notificationService.notifyBooked(sampleNotification));

        assertEquals("Failed to create booking notification", ex.getMessage());
    }

    @Test
    void testNotifyCancelledByPatientThrowsProcessingException() {
        when(notificationRepo.save(any())).thenThrow(new RuntimeException("DB error"));

        NotificationProcessingException ex = assertThrows(NotificationProcessingException.class, () ->
                notificationService.notifyCancelledByPatient(sampleNotification));

        assertEquals("Failed to create cancellation notification by patient", ex.getMessage());
    }

    @Test
    void testNotifyCancelledByDoctorThrowsProcessingException() {
        when(notificationRepo.save(any())).thenThrow(new RuntimeException("DB error"));

        NotificationProcessingException ex = assertThrows(NotificationProcessingException.class, () ->
                notificationService.notifyCancelledByDoctor(sampleNotification));

        assertEquals("Failed to create cancellation notification by doctor", ex.getMessage());
    }

    @Test
    void testGetNotificationsForPatientThrowsNotFoundException() {
        when(notificationRepo.findByPatientIdOrderByTimestampDesc(1)).thenReturn(Collections.emptyList());

        NotificationNotFoundException ex = assertThrows(NotificationNotFoundException.class, () ->
                notificationService.getNotificationsForPatient(1));

        assertEquals("No notifications found for patient ID: 1", ex.getMessage());
    }

    @Test
    void testGetNotificationsForDoctorThrowsNotFoundException() {
        when(notificationRepo.findByDoctorIdOrderByTimestampDesc(2)).thenReturn(Collections.emptyList());

        NotificationNotFoundException ex = assertThrows(NotificationNotFoundException.class, () ->
                notificationService.getNotificationsForDoctor(2));

        assertEquals("No notifications found for doctor ID: 2", ex.getMessage());
    }

    @Test
    void testGetNotificationsForPatientThrowsProcessingExceptionOnRepoError() {
        when(notificationRepo.findByPatientIdOrderByTimestampDesc(1))
            .thenThrow(new RuntimeException("DB error"));

        NotificationProcessingException ex = assertThrows(NotificationProcessingException.class, () ->
                notificationService.getNotificationsForPatient(1));

        assertTrue(ex.getMessage().contains("Failed to retrieve notifications for patient"));
    }

    @Test
    void testGetNotificationsForDoctorThrowsProcessingExceptionOnRepoError() {
        when(notificationRepo.findByDoctorIdOrderByTimestampDesc(2)).thenThrow(new RuntimeException("DB error"));

        NotificationProcessingException ex = assertThrows(NotificationProcessingException.class, () ->
                notificationService.getNotificationsForDoctor(2));

        assertEquals("Failed to retrieve notifications for doctor", ex.getMessage());
    }
}
