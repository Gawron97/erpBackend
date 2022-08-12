package git.erpBackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Operator {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOperator;
    private String login;
    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "idEmployee")
    private Employee employee;

}
