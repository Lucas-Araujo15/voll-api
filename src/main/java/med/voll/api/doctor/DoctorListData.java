package med.voll.api.doctor;

public record DoctorListData(Long id, String name, String email, String crm, Specialty specialty, String phone, Boolean active) {
    public DoctorListData(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty(), doctor.getPhone(), doctor.getActive());
    }
}
