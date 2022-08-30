package git.erpBackend.entity;


import git.erpBackend.dto.EmployeeDto;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude = "operator")
@EqualsAndHashCode
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmployee;
    private String pesel;
    private String name;
    private String surname;
    private String salary;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Operator operator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idPosition")
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public static Employee of(EmployeeDto employeeDto){
        Employee employee = new Employee();
        employee.name = employeeDto.getName();
        employee.surname = employeeDto.getSurname();
        employee.salary = employeeDto.getSalary();

        return employee;
    }

    public void updateEmployee(EmployeeDto employeeDto) {
        this.name = employeeDto.getName();
        this.surname = employeeDto.getSurname();
        this.salary = employeeDto.getSalary();
    }

}
