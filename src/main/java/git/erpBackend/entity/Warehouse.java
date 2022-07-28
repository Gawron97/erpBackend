package git.erpBackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Warehouse {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idWarehouse;

    @OneToOne(mappedBy = "warehouse")
    private Address address;

    @ManyToMany(mappedBy = "warehouses")
    private List<Item> items;

}
