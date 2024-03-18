package med.voll.api.domain.patient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Table(name = "patients")
@Entity(name= "Patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String phone;

    @Embedded
    private Address address;

    private Boolean active;

    public Patient(PatientRegisterData data) {
        this.name = data.name();
        this.phone = data.phone();
        this.address = new Address(data.address());
        this.email = data.email();
        this.active = true;
        this.cpf = data.cpf();
    }

    public void updateInformation(PatientUpdateData patientUpdateData) {
        if (patientUpdateData.name() != null) {
            this.name = patientUpdateData.name();
        }

        if (patientUpdateData.address() != null) {
            this.address.updateInformation(patientUpdateData.address());
        }

        if (patientUpdateData.phone() != null) {
            this.phone = patientUpdateData.phone();
        }
    }

    public void delete () {
        this.active = false;
    }

}
