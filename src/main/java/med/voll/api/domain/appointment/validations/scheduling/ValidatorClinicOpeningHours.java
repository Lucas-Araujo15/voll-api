package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidatorClinicOpeningHours implements ValidatorAppointmentScheduling {
    public void validate(AppointmentSchedulingData data) {
        LocalDateTime appointmentDate = data.date();

        int lastAvailableTime = 18;
        int firstAvailableTime = 7;

        Boolean isSunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        Boolean beforeClinicOpens = appointmentDate.getHour() < firstAvailableTime;
        Boolean afterClinicCloses = appointmentDate.getHour() > lastAvailableTime;

        if (isSunday || beforeClinicOpens || afterClinicCloses) {
            throw new ValidationException("Consulta fora do horário de funcionamento da clínica");
        }
    }
}
