package med.voll.api.domain.patient;

public record PatientListData(
        Long id,
        String name,
        String cpf,
        String phone,
        String email
) {
    public PatientListData(Patient patient) {
        this(patient.getId(),patient.getName(), patient.getCpf(), patient.getPhone(), patient.getEmail());
    }
}
