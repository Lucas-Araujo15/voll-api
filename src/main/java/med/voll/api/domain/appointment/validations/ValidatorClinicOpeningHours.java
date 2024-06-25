package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentSchedulingData;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class ValidatorClinicOpeningHours {
    public void validate(AppointmentSchedulingData data) {
        LocalDateTime appointmentDate = data.date();

        Boolean isSunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }
}
