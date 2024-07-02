package med.voll.api.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPatientIdAndCanceledAtIsNullAndDateBetween(Long patientId, LocalDateTime firstHour, LocalDateTime lastHour);

    boolean existsByDoctorIdAndDateAndCanceledAtIsNull(Long doctorId, LocalDateTime date);
}
