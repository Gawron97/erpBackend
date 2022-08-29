package git.erpBackend.entity;

import git.erpBackend.dto.AddressDto;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address address = (Address) o;
        return idAdress != null && Objects.equals(idAdress, address.idAdress);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
