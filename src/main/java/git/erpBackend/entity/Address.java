package git.erpBackend.entity;

import git.erpBackend.dto.AddressDto;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = "country")
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAdress;
    private String city;
    private String street;
    private Integer streetNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCountry")
    private Country country;

    public void setCountry(Country country) {
        this.country = country;
        country.addAddress(this);
    }

    public static Address of(AddressDto dto){
        Address address = new Address();
        address.idAdress = dto.getIdAddress();
        address.city = dto.getCity();
        address.street = dto.getStreet();
        address.streetNumber = dto.getStreetNumber();
        address.country = Country.of(dto.getCountryDto());

        return address;
    }
}
