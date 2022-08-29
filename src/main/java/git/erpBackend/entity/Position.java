package git.erpBackend.entity;

import git.erpBackend.enums.PositionEnum;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Position {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPosition;

    @Enumerated(EnumType.STRING)
    private PositionEnum positionEnum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Position position = (Position) o;
        return idPosition != null && Objects.equals(idPosition, position.idPosition);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}