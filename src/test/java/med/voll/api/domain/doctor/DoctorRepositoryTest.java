package med.voll.api.domain.doctor;

import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientRegisterData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Para testar uma interface repository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager em;

    @Test()
    @DisplayName("should return null when the only registered doctor is not available on this date")
    void chooseRandomDoctorFreeOnDateScenario1() {
        // given ou arrange
        LocalDateTime nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next((DayOfWeek.MONDAY)))
                .atTime(10, 0);

        Doctor doctor = createDoctor("Doctor", "doctor@voll.med", "123456", Specialty.CARDIOLOGY);
        Patient patient = createPatient("Patient", "patient@email.com", "00000000000");
        createAppointment(doctor, patient, nextMondayAt10);

        // when ou act
        Doctor freeDoctor = doctorRepository.chooseRandomDoctorFreeOnDate(Specialty.CARDIOLOGY, nextMondayAt10);

        // then ou assert
        assertThat(freeDoctor).isNull();
    }

    @Test()
    @DisplayName("should return doctor when it is available on this date")
    void chooseRandomDoctorFreeOnDateScenario2() {
        // given ou arrange
        LocalDateTime nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next((DayOfWeek.MONDAY)))
                .atTime(10, 0);

        Doctor doctor = createDoctor("Doctor", "doctor@voll.med", "123456", Specialty.CARDIOLOGY);

        // when ou act
        Doctor freeDoctor = doctorRepository.chooseRandomDoctorFreeOnDate(Specialty.CARDIOLOGY, nextMondayAt10);

        // then ou assert
        assertThat(freeDoctor).isEqualTo(doctor);
    }

    private void createAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        em.persist(new Appointment(doctor, patient, date));
    }

    private Doctor createDoctor(String name, String email, String crm, Specialty specialty) {
        Doctor doctor = new Doctor(doctorData(name, email, crm, specialty));
        em.persist(doctor);
        return doctor;
    }

    private Patient createPatient(String name, String email, String cpf) {
        Patient patient = new Patient(patientData(name, email, cpf));
        em.persist(patient);
        return patient;
    }

    private DoctorRegisterData doctorData(String name, String email, String crm, Specialty specialty) {
        return new DoctorRegisterData(
                name,
                email,
                "61999999999",
                crm,
                specialty,
                addressData()
        );
    }

    private PatientRegisterData patientData(String name, String email, String cpf) {
        return new PatientRegisterData(
                name,
                cpf,
                email,
                "61999999999",
                addressData()
        );
    }

    private AddressData addressData() {
        return new AddressData(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}