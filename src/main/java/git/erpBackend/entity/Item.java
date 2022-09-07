package git.erpBackend.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    public void removeWarehouse(Warehouse warehouse){
        this.warehouse = null;
        warehouse.removeItem(this);
    }

}
