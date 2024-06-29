package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidatorPatientWithoutAnotherAppointmentAtTheDay implements ValidatorAppointmentScheduling {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate(AppointmentSchedulingData data) {
        int firstAvailableTime = 7;
        int lastAvailableTime = 18;

        LocalDateTime firstHour = data.date().withHour(firstAvailableTime);
        LocalDateTime lastHour = data.date().withHour(lastAvailableTime);

        boolean patientHasAnotherAppointmentAtTheDay = appointmentRepository.existsByPatientIdAndDateBetween(data.patientId(), firstHour, lastHour);

        if (patientHasAnotherAppointmentAtTheDay) {
            throw new ValidationException("Paciente já possui uma consulta agendada nesse dia");
        }
    }
}
