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
public class Truck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTruck;
    private String name;
    private double capacity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Truck truck = (Truck) o;
        return idTruck != null && Objects.equals(idTruck, truck.idTruck);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
