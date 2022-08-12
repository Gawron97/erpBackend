package git.erpBackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ItemSum {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idQuantityType")
    private QuantityType quantityType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "itemSum_warehouses", joinColumns = @JoinColumn(name = "IdItemSum"),
            inverseJoinColumns = @JoinColumn(name = "IdWarehouse"))
    private List<Warehouse> warehouses;

    public ItemSum(){
        warehouses = new ArrayList<>();
    }

    public void addWarehouse(Warehouse warehouse){

        warehouses.add(warehouse);
        warehouse.addItemSum(this);
    }

}
