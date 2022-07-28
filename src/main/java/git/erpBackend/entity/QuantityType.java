package git.erpBackend.entity;

import git.erpBackend.enums.QuantityEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class QuantityType {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idQuantityType;

    @Enumerated(EnumType.STRING)
    private QuantityEnum quantityType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quantityType", cascade = CascadeType.REMOVE)
    private List<Item> items;


}
