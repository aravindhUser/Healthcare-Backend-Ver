package com.cts.notification.service;

import com.cts.notification.model.Notification;
import com.cts.notification.repo.NotificationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Test
    void testNotifyBooked() {
        Notification result = notificationService.notifyBooked(sampleNotification);

        assertNotNull(result.getTimestamp());
        assertTrue(result.getMessage().contains("appointment successfully booked"));
        verify(notificationRepo, times(1)).save(sampleNotification);
    }

    @Test
    void testNotifyCancelledByPatient() {
        Notification result = notificationService.notifyCancelledByPatient(sampleNotification);

        assertNotNull(result.getTimestamp());
        assertTrue(result.getMessage().contains("cancelled by Patient"));
        verify(notificationRepo, times(1)).save(sampleNotification);
    }

    @Test
    void testNotifyCancelledByDoctor() {
        Notification result = notificationService.notifyCancelledByDoctor(sampleNotification);

        assertNotNull(result.getTimestamp());
        assertTrue(result.getMessage().contains("cancelled by Doctor"));
        verify(notificationRepo, times(1)).save(sampleNotification);
    }

    @Test
    void testGetNotificationsForPatient() {
        when(notificationRepo.findByPatientIdOrderByTimestampDesc(1)).thenReturn(List.of(sampleNotification));

        List<Notification> result = notificationService.getNotificationsForPatient(1);

        assertEquals(1, result.size());
        assertEquals(sampleNotification, result.get(0));
        verify(notificationRepo, times(1)).findByPatientIdOrderByTimestampDesc(1);
    }

    @Test
    void testGetNotificationsForDoctor() {
        when(notificationRepo.findByDoctorIdOrderByTimestampDesc(2)).thenReturn(List.of(sampleNotification));

        List<Notification> result = notificationService.getNotificationsForDoctor(2);

        assertEquals(1, result.size());
        assertEquals(sampleNotification, result.get(0));
        verify(notificationRepo, times(1)).findByDoctorIdOrderByTimestampDesc(2);
    }
}
