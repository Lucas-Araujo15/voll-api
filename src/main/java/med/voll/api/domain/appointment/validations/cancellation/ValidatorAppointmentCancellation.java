package med.voll.api.domain.appointment.validations.cancellation;

import med.voll.api.domain.appointment.AppointmentCancellationData;

public interface ValidatorAppointmentCancellation {
    void validate(AppointmentCancellationData data);
}
