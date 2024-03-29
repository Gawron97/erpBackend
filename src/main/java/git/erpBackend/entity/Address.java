package git.erpBackend.entity;

import git.erpBackend.dto.AddressDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
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

    public static Address of(String city, String street, Integer streetNumber, Country country) {
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setStreetNumber(streetNumber);
        address.setCountry(country);

        return address;
    }

}
