package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.doctor.DoctorRepository;

public class ValidatorActiveDoctor {
    private DoctorRepository doctorRepository;

    public void validate(AppointmentSchedulingData data) {
        if (data.doctorId() == null) {
            return;
        }

        Boolean isDoctorActive = doctorRepository.findActiveById(data.doctorId());

        if (!isDoctorActive) {
            throw new ValidationException("Consulta não pode ser agendada com médico");
        }
    }
}
