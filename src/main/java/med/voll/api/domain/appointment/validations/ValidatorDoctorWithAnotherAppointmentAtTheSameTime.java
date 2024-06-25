package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentSchedulingData;

public class ValidatorDoctorWithAnotherAppointmentAtTheSameTime {
    private AppointmentRepository appointmentRepository;

    public void validate (AppointmentSchedulingData data) {
        boolean doctorHasAnotherAppointmentAtTheSameTime = appointmentRepository.existsByDoctorIdAndDate(data.doctorId(), data.date());

        if (doctorHasAnotherAppointmentAtTheSameTime) {
            throw new ValidationException("Médico já possui outra consulta agendada nesse mesmo horário");
        }
    }
}
