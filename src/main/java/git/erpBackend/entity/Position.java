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
@EqualsAndHashCode
public class Position {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPosition;

    @Enumerated(EnumType.STRING)
    private PositionEnum positionEnum;

}