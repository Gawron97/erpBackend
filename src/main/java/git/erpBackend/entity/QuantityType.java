package git.erpBackend.entity;

import git.erpBackend.enums.QuantityEnum;
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
public class QuantityType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuantityType;

    @Enumerated(EnumType.STRING)
    private QuantityEnum quantityType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quantityType")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Item> items;

    public QuantityType(){
        items = new ArrayList<>();
    }

    public void addItem(Item item){
        items.add(item);
    }

}
