package git.erpBackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Warehouse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWarehouse;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idAddress")
    private Address address;

    @ManyToMany(mappedBy = "warehouses")
    private List<Item> items;


}
