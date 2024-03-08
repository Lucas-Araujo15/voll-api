package med.voll.api.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String neighborhood;
    private String zipcode;
    private String number;
    private String complement;
    private String city;
    private String uf;

    public Address(AddressData address) {
        this.street = address.street();
        this.neighborhood = address.neighborhood();
        this.uf = address.uf();
        this.city = address.city();
        this.zipcode = address.zipcode();
        this.number = address.number();
        this.complement = address.complement();
    }

    public void updateInformation(AddressData data) {
        if (data.city() != null) {
            this.city = data.city();
        }

        if (data.complement() != null) {
            this.complement = data.complement();
        }

        if (data.uf() != null) {
            this.uf = data.uf();
        }

        if (data.number() != null) {
            this.number = data.number();
        }

        if (data.neighborhood() != null) {
            this.neighborhood = data.neighborhood();
        }

        if (data.street() != null) {
            this.street = data.street();
        }

        if (data.zipcode() != null) {
            this.zipcode = data.zipcode();
        }


    }
}
