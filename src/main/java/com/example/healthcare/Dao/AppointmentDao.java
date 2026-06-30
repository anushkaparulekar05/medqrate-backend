package com.example.healthcare.Dao;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.healthcare.Entity.Appointment;
import com.example.healthcare.Entity.User;
import com.example.healthcare.Repository.AppointmentRepository;
import com.example.healthcare.Repository.UserRepository;

import java.util.List;

@Service
public class AppointmentDao {

    private final AppointmentRepository appointmentRepo;
    private final UserRepository userRepo;

    public AppointmentDao(AppointmentRepository appointmentRepo, UserRepository userRepo) {
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
    }

    // ================= BOOK =================
    public Appointment bookAppointment(Long patientId, Long doctorId, Appointment appointment) {
        User patient = userRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        User doctor = userRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus("PENDING");

        return appointmentRepo.save(appointment);
    }

    // ================= APPROVE + PRESCRIPTION =================
    public Appointment approveAppointment(Long appointmentId, String imagePath, String prescriptionNotes) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("APPROVED");
        if (imagePath != null && !imagePath.isBlank()) {
            appointment.setImagePath(imagePath);
        }
        if (prescriptionNotes != null && !prescriptionNotes.isBlank()) {
            appointment.setPrescriptionNotes(prescriptionNotes);
        }
        return appointmentRepo.save(appointment);
    }

    // ================= REJECT =================
    public ResponseEntity<?> rejectAppointment(Long appointmentId, String reason) {
        return appointmentRepo.findById(appointmentId).map(appt -> {
            appt.setStatus("REJECTED");
            if (reason != null && !reason.isBlank()) {
                appt.setPrescriptionNotes("Rejected: " + reason);
            }
            return ResponseEntity.ok(appointmentRepo.save(appt));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ================= CANCEL =================
    public ResponseEntity<?> cancelAppointment(Long appointmentId) {
        return appointmentRepo.findById(appointmentId).map(appt -> {
            appt.setStatus("CANCELLED");
            return ResponseEntity.ok(appointmentRepo.save(appt));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ================= DOCTOR APPOINTMENTS =================
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepo.findByDoctor_IdOrderByCreatedAtDesc(doctorId);
    }

    // ================= PATIENT APPOINTMENTS =================
    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepo.findByPatient_IdOrderByCreatedAtDesc(patientId);
    }

    // ================= GET BY ID =================
    public ResponseEntity<?> getAppointmentById(Long appointmentId) {
        return appointmentRepo.findById(appointmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================= DELETE =================
    public ResponseEntity<?> deleteAppointment(Long appointmentId) {
        if (!appointmentRepo.existsById(appointmentId)) {
            return ResponseEntity.notFound().build();
        }
        appointmentRepo.deleteById(appointmentId);
        return ResponseEntity.ok("Appointment deleted successfully.");
    }

    // ================= ALL APPOINTMENTS (Admin) =================
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }
}