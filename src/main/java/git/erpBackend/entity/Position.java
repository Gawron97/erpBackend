package git.erpBackend.entity;

import git.erpBackend.enums.PositionEnum;
import jakarta.persistence.*;
import lombok.*;

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