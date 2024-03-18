package med.voll.api.domain.patient;

import med.voll.api.domain.address.Address;

public record DetailedPatientData(
        Long id,
        String name,
        String cpf,
        String phone,
        String email,
        Address address
) {
    public DetailedPatientData(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getCpf(), patient.getPhone(), patient.getEmail(), patient.getAddress());
    }
}
