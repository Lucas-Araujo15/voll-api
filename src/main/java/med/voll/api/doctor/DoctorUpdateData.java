package med.voll.api.doctor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.address.AddressData;

public record DoctorUpdateData(
        @NotNull
        Long id,
        String name,
        String phone,
        AddressData address) {
}
