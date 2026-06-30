package com.example.healthcare.Service;

import org.springframework.http.ResponseEntity;
import com.example.healthcare.Entity.Appointment;
import java.util.List;

public interface AppointmentService {

    Appointment bookAppointment(Long patientId, Long doctorId, Appointment appointment);

    Appointment approveAppointment(Long appointmentId, String imagePath, String prescriptionNotes);

    ResponseEntity<?> rejectAppointment(Long appointmentId, String reason);

    ResponseEntity<?> cancelAppointment(Long appointmentId);

    List<Appointment> getDoctorAppointments(Long doctorId);

    List<Appointment> getPatientAppointments(Long patientId);

    ResponseEntity<?> getAppointmentById(Long appointmentId);

    ResponseEntity<?> deleteAppointment(Long appointmentId);

    List<Appointment> getAllAppointments();
}