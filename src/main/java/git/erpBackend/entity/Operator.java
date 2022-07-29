package git.erpBackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Operator {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idOperator;
    private String login;
    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idEmployee")
    private Employee employee;

}
