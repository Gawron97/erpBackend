package git.erpBackend.entity;

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


}
