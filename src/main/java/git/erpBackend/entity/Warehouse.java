package git.erpBackend.entity;

import git.erpBackend.dto.WarehouseDto;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Warehouse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWarehouse;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idAddress")
    private Address address;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Item> items;

    @ManyToMany(mappedBy = "warehouses", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ItemSum> itemSums;

    public Warehouse(){
        items = new ArrayList<>();
        itemSums = new ArrayList<>();
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void addItemSum(ItemSum itemSum){
        itemSums.add(itemSum);
    }

    public static Warehouse of(WarehouseDto dto){
        Warehouse warehouse = new Warehouse();

        warehouse.idWarehouse = dto.getIdWarehouse();
        warehouse.name = dto.getName();
        warehouse.address = Address.of(dto.getAddressDto());

        return warehouse;

    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void removeItemSum(ItemSum itemSum) {
        itemSums.remove(itemSum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Warehouse warehouse = (Warehouse) o;
        return idWarehouse != null && Objects.equals(idWarehouse, warehouse.idWarehouse);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
