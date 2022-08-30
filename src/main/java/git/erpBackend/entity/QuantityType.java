package git.erpBackend.entity;

import git.erpBackend.enums.QuantityEnum;
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
