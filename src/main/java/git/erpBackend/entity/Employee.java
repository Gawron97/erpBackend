package git.erpBackend.entity;


import git.erpBackend.dto.EmployeeDto;
import lombok.Data;
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
    private String name;

    @Setter @Getter
    private String surname;

    @Setter @Getter
    private String salary;


    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Operator operator;

    public static Employee of(EmployeeDto employeeDto){
        Employee employee = new Employee();
        employee.name = employeeDto.getName();
        employee.surname = employeeDto.getSurname();
        employee.salary = employeeDto.getSalary();

        return employee;
    }

}
