package git.erpBackend.entity;

import git.erpBackend.enums.QuantityEnum;
import git.erpBackend.repository.QuantityTypeRepository;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class QuantityType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuantityType;

    @Enumerated(EnumType.STRING)
    private QuantityEnum quantityType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quantityType")
    private List<Item> items;

    public QuantityType(){
        items = new ArrayList<>();
    }

    public void addItem(Item item){
        items.add(item);
    }


}
