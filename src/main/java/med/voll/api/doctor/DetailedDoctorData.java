package med.voll.api.doctor;

import med.voll.api.address.Address;

public record DetailedDoctorData(Long id, String name, String email, String crm, String phone, Specialty specialty, Address address) {
    public DetailedDoctorData(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getPhone(), doctor.getSpecialty(), doctor.getAddress());
    }
}
