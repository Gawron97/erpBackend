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
public class QuantityType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQuantityType;

    @Enumerated(EnumType.STRING)
    private QuantityEnum quantityType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quantityType")
    @ToString.Exclude
    private List<Item> items;

    public QuantityType(){
        items = new ArrayList<>();
    }

    public void addItem(Item item){
        items.add(item);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuantityType that = (QuantityType) o;
        return idQuantityType != null && Objects.equals(idQuantityType, that.idQuantityType);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
