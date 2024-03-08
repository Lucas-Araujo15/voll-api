package med.voll.api.doctor;

public record DoctorListData(String name, String email, String crm, Specialty specialty) {
    public DoctorListData(Doctor doctor) {
        this(doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty());
    }
}
