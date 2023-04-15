package git.erpBackend.entity;

import git.erpBackend.dto.OperatorRegisterCredentialsDto;
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
public class Operator {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOperator;
    private String login;
    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "idEmployee")
    private Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;
        employee.setOperator(this);
    }

    public static Operator of(String login, String password, Employee employee){
        Operator operator = new Operator();
        operator.login = login;
        operator.password = password;
        operator.employee = employee;

        return operator;
    }

}
