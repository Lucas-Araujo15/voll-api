package med.voll.api.domain.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByActiveTrue(Pageable pagination);

    @Query("""
            select d from doctors d
            where
            d.active = true
            and
            m.specialty = :specialty
            and
            m.id not in (
                select a.doctor_id from appointments a
                where
                a.date = :date
            )
            order by rand()
            limit 1
            """)
    Doctor chooseRandomDoctorFreeOnDate(Specialty specialty, LocalDateTime date);

    @Query("""
            select d.active
            from doctors d
            where d.id = :id
            """)
    Boolean findActiveById(Long doctorId);
}
