package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidatorAdvanceTime implements ValidatorAppointmentScheduling {
    public void validate(AppointmentSchedulingData data) {
        LocalDateTime appointmentDate = data.date();
        LocalDateTime now = LocalDateTime.now();
        long differenceInMinutes = Duration.between(now, appointmentDate).toMinutes();

        int minimumAdvanceTime = 30;

        if (differenceInMinutes < minimumAdvanceTime) {
            throw new ValidationException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }
}
