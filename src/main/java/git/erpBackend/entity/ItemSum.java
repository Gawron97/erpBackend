package git.erpBackend.entity;

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
public class ItemSum {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idQuantityType")
    private QuantityType quantityType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "itemSum_warehouses", joinColumns = @JoinColumn(name = "IdItemSum"),
            inverseJoinColumns = @JoinColumn(name = "IdWarehouse"))
    @ToString.Exclude
    private List<Warehouse> warehouses;

    public ItemSum(){
        warehouses = new ArrayList<>();
    }

    public void addWarehouse(Warehouse warehouse){

        warehouses.add(warehouse);
        warehouse.addItemSum(this);
    }

    public void removeWarehouse(Warehouse warehouse) {
        warehouses.remove(warehouse);
        warehouse.removeItemSum(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemSum itemSum = (ItemSum) o;
        return id != null && Objects.equals(id, itemSum.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
