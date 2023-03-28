package git.erpBackend.service;

import git.erpBackend.dto.EmployeeDto;
import git.erpBackend.entity.Employee;
import git.erpBackend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public ResponseEntity saveOrUpdateEmployee(EmployeeDto employeeDto){
        Employee employee;
        if(employeeDto.getIdEmployee() == null) {
            employee = Employee.of(employeeDto);
        }
        else {
            Optional<Employee> optionalEmployee = employeeRepository.findById(employeeDto.getIdEmployee());
            employee = optionalEmployee.orElseThrow(() -> {
                throw new RuntimeException("Nie mozna znalezc takiego uzytownika");
            });
            employee.setName(employeeDto.getName());
            employee.setSurname(employeeDto.getSurname());
            employee.setSalary(employeeDto.getSalary());
        }
        employeeRepository.save(employee);
        return ResponseEntity.ok().build();
    }

    public List<EmployeeDto> getEmployeeList(){
        return employeeRepository.findAll().stream().map(employee -> EmployeeDto.of(employee)).collect(Collectors.toList());
    }

    public EmployeeDto getEmployee(Integer idEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(idEmployee);
        Employee employee = optionalEmployee.orElseThrow(() -> {
            throw new RuntimeException("nie znaleziono pracownika");
        });
        return EmployeeDto.of(employee);
    }

    public ResponseEntity deleteEmployee(Integer idEmployee){
        employeeRepository.deleteById(idEmployee);
        return ResponseEntity.ok().build();
    }
}
