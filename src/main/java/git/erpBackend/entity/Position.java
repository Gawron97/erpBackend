package git.erpBackend.entity;

import git.erpBackend.enums.PositionEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Position {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPosition;

    @Enumerated(EnumType.STRING)
    private PositionEnum positionEnum;


}