package med.voll.api.domain.appointment;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.validations.ValidatorAppointmentScheduling;
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

    public void schedule(AppointmentSchedulingData data) {
        if (data.doctorId() != null && !doctorRepository.existsById(data.doctorId())) {
            throw new ValidationException("ID do médico informado não existe");
        }

        if (!patientRepository.existsById(data.patientId())) {
            throw new ValidationException("ID do paciente informado não existe");
        }

        validators.forEach(validator -> validator.validate(data));

        Doctor doctor = chooseDoctor(data);
        Patient patient = patientRepository.findById(data.patientId()).get();

        Appointment appointment = new Appointment(null, doctor, patient, data.date());
        appointmentRepository.save(appointment);
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
