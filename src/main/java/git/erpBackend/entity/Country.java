package git.erpBackend.entity;

import git.erpBackend.dto.CountryDto;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Country {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCountry;
    private String name;
    private int warehousesAmount;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Address> addresses;

    public Country(){
        addresses = new ArrayList<>();
    }

    public void addAddress(Address address){
        addresses.add(address);
    }

    public static Country of(CountryDto dto){
        Country country = new Country();
        country.idCountry = dto.getIdCountry();
        country.name = dto.getCountry();

        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Country country = (Country) o;
        return idCountry != null && Objects.equals(idCountry, country.idCountry);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
