package med.voll.api.domain.appointment;

import jakarta.validation.Valid;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentScheduling {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    public void schedule(AppointmentSchedulingData data) {
        Doctor doctor = doctorRepository.findById(data.doctorId()).get();
        Patient patient = patientRepository.findById(data.patientId()).get();


        Appointment appointment = new Appointment(null, doctor, patient, data.date());
        appointmentRepository.save(appointment);
    }
}
