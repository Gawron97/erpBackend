package git.erpBackend.entity;

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

    @ManyToMany(mappedBy = "warehouses", fetch = FetchType.LAZY)
    private List<Item> items;

    public Warehouse(){
        items = new ArrayList<>();
    }

    public void addItem(Item item){
        items.add(item);
    }




}
