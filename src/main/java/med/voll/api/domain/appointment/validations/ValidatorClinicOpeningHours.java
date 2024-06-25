package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class ValidatorClinicOpeningHours {
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
