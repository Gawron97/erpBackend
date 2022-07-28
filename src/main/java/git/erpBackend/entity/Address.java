package git.erpBackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idAdress;
    private String city;
    private String street;
    private int streetNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idCountry")
    private Country country;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idWarehouse", referencedColumnName = "idWarehouse")
    private Warehouse warehouse;



}
