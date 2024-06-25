package med.voll.api.domain.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAllByActiveTrue(Pageable pagination);

    Optional<Patient> findByIdAndActiveTrue(Long id);

    @Query("""
            select p.active
            from Patient p
            where p.id = :id
            """)
    boolean findActiveById(Long id);
}
