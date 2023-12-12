package git.erpBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class Truck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTruck;
    private String name;
    private double capacity;

}
