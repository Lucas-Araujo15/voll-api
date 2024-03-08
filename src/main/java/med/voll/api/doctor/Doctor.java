package med.voll.api.doctor;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.address.Address;

@Table(name = "doctors")
@Entity(name= "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String crm;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Embedded
    private Address address;

    public Doctor(DoctorRegisterData data) {
        this.name = data.name();
        this.crm = data.crm();
        this.address = new Address(data.address());
        this.crm = data.crm();
        this.email = data.email();
        this.specialty = data.specialty();
        this.phone = data.phone();
    }
}
