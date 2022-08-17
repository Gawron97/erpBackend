package git.erpBackend.entity;

import git.erpBackend.dto.WarehouseDto;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Warehouse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWarehouse;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idAddress")
    private Address address;

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Item> items;

    @ManyToMany(mappedBy = "warehouses", fetch = FetchType.LAZY)
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
}
