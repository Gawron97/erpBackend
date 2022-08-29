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

    public static Operator of(OperatorRegisterCredentialsDto dto){
        Operator operator = new Operator();
        operator.login = dto.getLogin();
        operator.password = dto.getPassword();

        return operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Operator operator = (Operator) o;
        return idOperator != null && Objects.equals(idOperator, operator.idOperator);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
