package med.voll.api.domain.appointment;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.validations.cancellation.ValidatorAppointmentCancellation;
import med.voll.api.domain.appointment.validations.scheduling.ValidatorAppointmentScheduling;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentScheduling {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<ValidatorAppointmentScheduling> validators;

    @Autowired
    private List<ValidatorAppointmentCancellation> validatorsCancellation;

    public DetailedAppointmentData schedule(AppointmentSchedulingData data) {
        if (data.doctorId() != null && !doctorRepository.existsById(data.doctorId())) {
            throw new ValidationException("ID do médico informado não existe");
        }

        if (!patientRepository.existsById(data.patientId())) {
            throw new ValidationException("ID do paciente informado não existe");
        }

        validators.forEach(validator -> validator.validate(data));

        Doctor doctor = chooseDoctor(data);

        if (doctor == null) {
            throw new ValidationException("Não existe médico disponível para essa data");
        }

        Patient patient = patientRepository.findById(data.patientId()).get();

        Appointment appointment = new Appointment(doctor, patient, data.date());
        appointmentRepository.save(appointment);

        return new DetailedAppointmentData(appointment);
    }

    public void cancel(AppointmentCancellationData data) {
        if (!appointmentRepository.existsById(data.appointmentId())) {
            throw new ValidationException("Id da consulta informado não existe!");
        }

        validatorsCancellation.forEach(validator -> validator.validate(data));

        Appointment appointment = appointmentRepository.getReferenceById(data.appointmentId());

        appointment.cancel(data.reason());
    }

    private Doctor chooseDoctor(AppointmentSchedulingData data) {
        if (data.doctorId() != null) {
            return doctorRepository.getReferenceById(data.doctorId());
        }

        if (data.specialty() == null) {
            throw new ValidationException("Especialidade é obrigatória quando o médico não for escolhido");
        }

        return doctorRepository.chooseRandomDoctorFreeOnDate(data.specialty(), data.date());
    }
}
