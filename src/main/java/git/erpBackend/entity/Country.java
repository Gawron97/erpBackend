package git.erpBackend.entity;

import jdk.jfr.DataAmount;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Country {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCountry;
    private String name;
    private int warehousesAmount;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Address> addresses;

    public Country(){
        addresses = new ArrayList<>();
    }

    public void addAddress(Address address){
        addresses.add(address);
    }


}
