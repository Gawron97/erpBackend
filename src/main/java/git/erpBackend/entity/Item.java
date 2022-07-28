package git.erpBackend.entity;

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idItem;
    private String name;
    private double quantity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "idQuantityType")
    private QuantityType quantityType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_warehouse", joinColumns = @JoinColumn(name = "idItem"),
            inverseJoinColumns = @JoinColumn(name = "idWarehouse"))
    private List<Warehouse> warehouses;

}
