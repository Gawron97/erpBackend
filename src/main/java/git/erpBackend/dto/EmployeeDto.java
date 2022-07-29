package git.erpBackend.dto;

import git.erpBackend.entity.Employee;
import lombok.Data;

@Data
public class EmployeeDto {

    private Integer idEmployee;
    private String name;
    private String surname;
    private String salary;

    public static EmployeeDto of(Employee employee){
        EmployeeDto dto = new EmployeeDto();
        dto.idEmployee = employee.getIdEmployee();
        dto.name = employee.getName();
        dto.surname = employee.getSurname();
        dto.salary = employee.getSalary();

        return dto;
    }

}
