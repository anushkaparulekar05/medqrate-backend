package com.example.healthcare.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.healthcare.Dao.AppointmentDao;
import com.example.healthcare.Entity.Appointment;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDao appointmentDao;

    public AppointmentServiceImpl(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    @Override
    public Appointment bookAppointment(Long patientId, Long doctorId, Appointment appointment) {
        return appointmentDao.bookAppointment(patientId, doctorId, appointment);
    }

    @Override
    public Appointment approveAppointment(Long appointmentId, String imagePath, String prescriptionNotes) {
        return appointmentDao.approveAppointment(appointmentId, imagePath, prescriptionNotes);
    }

    @Override
    public ResponseEntity<?> rejectAppointment(Long appointmentId, String reason) {
        return appointmentDao.rejectAppointment(appointmentId, reason);
    }

    @Override
    public ResponseEntity<?> cancelAppointment(Long appointmentId) {
        return appointmentDao.cancelAppointment(appointmentId);
    }

    @Override
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentDao.getDoctorAppointments(doctorId);
    }

    @Override
    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentDao.getPatientAppointments(patientId);
    }

    @Override
    public ResponseEntity<?> getAppointmentById(Long appointmentId) {
        return appointmentDao.getAppointmentById(appointmentId);
    }

    @Override
    public ResponseEntity<?> deleteAppointment(Long appointmentId) {
        return appointmentDao.deleteAppointment(appointmentId);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentDao.getAllAppointments();
    }
}