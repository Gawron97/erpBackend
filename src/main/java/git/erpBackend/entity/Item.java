package git.erpBackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;
    private String name;
    private double quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idQuantityType")
    private QuantityType quantityType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idWarehouse")
    private Warehouse warehouse;

    public void setQuantityType(QuantityType quantityType) {
        this.quantityType = quantityType;
        quantityType.addItem(this);
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
        warehouse.addItem(this);
    }

}
