package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorActivePatient implements ValidatorAppointmentScheduling {
    @Autowired
    private PatientRepository patientRepository;

    public void validate(AppointmentSchedulingData data) {
        boolean isPatientActive = patientRepository.findActiveById(data.patientId());

        if (!isPatientActive) {
            throw new ValidationException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
