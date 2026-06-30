package com.example.healthcare.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.healthcare.Entity.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Ordered by newest first
    List<Appointment> findByDoctor_IdOrderByCreatedAtDesc(Long doctorId);
    List<Appointment> findByPatient_IdOrderByCreatedAtDesc(Long patientId);

    // Old queries kept for compatibility
    List<Appointment> findByDoctor_Id(Long doctorId);
    List<Appointment> findByPatient_Id(Long patientId);

    // By status
    List<Appointment> findByStatus(String status);
    List<Appointment> findByDoctor_IdAndStatus(Long doctorId, String status);
    List<Appointment> findByPatient_IdAndStatus(Long patientId, String status);
}