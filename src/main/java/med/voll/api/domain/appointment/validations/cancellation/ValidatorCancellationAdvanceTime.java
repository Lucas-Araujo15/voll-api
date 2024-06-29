package med.voll.api.domain.appointment.validations.cancellation;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentCancellationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidatorCancellationAdvanceTime implements ValidatorAppointmentCancellation {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate(AppointmentCancellationData data) {
        Appointment appointment = appointmentRepository.getReferenceById(data.appointmentId());
        LocalDateTime now = LocalDateTime.now();
        long differenceInHours = Duration.between(now, appointment.getDate()).toHours();

        int oneDayInHours = 24;

        if (differenceInHours < oneDayInHours) {
            throw new ValidationException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}
