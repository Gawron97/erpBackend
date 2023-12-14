package git.erpBackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;
    private String name;
    private double quantity;
    private double price;

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

    public void removeWarehouse(){
        this.warehouse = null;
    }

}
