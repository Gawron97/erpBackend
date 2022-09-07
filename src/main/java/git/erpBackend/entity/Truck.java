package git.erpBackend.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

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