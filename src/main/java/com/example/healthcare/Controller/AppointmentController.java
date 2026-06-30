package com.example.healthcare.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthcare.Entity.Appointment;
import com.example.healthcare.Service.AppointmentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // ================= BOOK APPOINTMENT =================
    @PostMapping("/book/{patientId}/{doctorId}")
    public Appointment bookAppointment(@PathVariable Long patientId,
                                       @PathVariable Long doctorId,
                                       @RequestBody Appointment appointment) {
        return appointmentService.bookAppointment(patientId, doctorId, appointment);
    }

    // ================= APPROVE & SEND PRESCRIPTION =================
    @PutMapping("/approve/{appointmentId}")
    public Appointment approveAppointment(@PathVariable Long appointmentId,
                                          @RequestBody Appointment updatedAppointment) {
        return appointmentService.approveAppointment(
                appointmentId,
                updatedAppointment.getImagePath(),
                updatedAppointment.getPrescriptionNotes()
        );
    }

    // ================= REJECT APPOINTMENT =================
    @PutMapping("/reject/{appointmentId}")
    public ResponseEntity<?> rejectAppointment(@PathVariable Long appointmentId,
                                               @RequestBody(required = false) Map<String, String> body) {
        String reason = body != null ? body.get("reason") : "";
        return appointmentService.rejectAppointment(appointmentId, reason);
    }

    // ================= CANCEL APPOINTMENT (Patient) =================
    @PutMapping("/cancel/{appointmentId}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long appointmentId) {
        return appointmentService.cancelAppointment(appointmentId);
    }

    // ================= GET DOCTOR'S APPOINTMENTS =================
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getDoctorAppointments(@PathVariable Long doctorId) {
        return appointmentService.getDoctorAppointments(doctorId);
    }

    // ================= GET PATIENT'S APPOINTMENTS =================
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getPatientAppointments(@PathVariable Long patientId) {
        return appointmentService.getPatientAppointments(patientId);
    }

    // ================= GET APPOINTMENT BY ID =================
    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    // ================= DELETE APPOINTMENT =================
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long appointmentId) {
        return appointmentService.deleteAppointment(appointmentId);
    }

    // ================= GET ALL APPOINTMENTS (Admin) =================
    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}