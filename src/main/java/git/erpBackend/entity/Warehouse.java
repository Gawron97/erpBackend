package git.erpBackend.entity;

import git.erpBackend.dto.WarehouseDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Warehouse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWarehouse;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idAddress")
    private Address address;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Item> items;

    @ManyToMany(mappedBy = "warehouses", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ItemSum> itemSums;

    public Warehouse() {
        items = new ArrayList<>();
        itemSums = new ArrayList<>();
    }

    public Warehouse(String name, Address address) {
        this.name = name;
        this.address = address;
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

}
