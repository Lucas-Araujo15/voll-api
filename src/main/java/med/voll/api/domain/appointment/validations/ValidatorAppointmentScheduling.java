package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentSchedulingData;

public interface ValidatorAppointmentScheduling {
    void validate(AppointmentSchedulingData data);
}
