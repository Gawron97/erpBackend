package git.erpBackend.entity;


import git.erpBackend.dto.EmployeeDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString(exclude = "operator")
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter
    private Integer idEmployee;

    @Setter @Getter
    private String pesel;

    @Setter @Getter
    private String name;

    @Setter @Getter
    private String surname;

    @Setter @Getter
    private String salary;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @Getter @Setter
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
