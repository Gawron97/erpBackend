package git.erpBackend.controller;

import git.erpBackend.dto.EmployeeDto;
import git.erpBackend.entity.Employee;
import git.erpBackend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public EmployeeDto saveOrUpdateEmployee(@RequestBody EmployeeDto employeeDto){
        if(employeeDto.getIdEmployee() == null)
            return EmployeeDto.of(employeeRepository.save(Employee.of(employeeDto)));
        else{
            Optional<Employee> optionalEmployee = employeeRepository.findById(employeeDto.getIdEmployee());
            if(optionalEmployee.isPresent()){
                Employee employee = optionalEmployee.get();
                employee.updateEmployee(employeeDto);
                return EmployeeDto.of(employeeRepository.save(employee));
            }else {
                throw new RuntimeException("Nie mozna znalezc uzytkownika z takim Id");
            }
        }
    }

    @GetMapping("/employees")
    public List<EmployeeDto> listEmployees(){
        return employeeRepository.findAll().stream().map(employee -> {
            return EmployeeDto.of(employee);
        }).collect(Collectors.toList());
    }

    @GetMapping("/employees/{idEmployee}")
    public EmployeeDto getEmployee(@PathVariable Integer idEmployee) throws InterruptedException {
        Thread.sleep(2000);
        Optional<Employee> employee = employeeRepository.findById(idEmployee);
        return EmployeeDto.of(employee.get());
    }

    @DeleteMapping("/employees/{idEmployee}")
    public ResponseEntity deleteEmployee(@PathVariable Integer idEmployee){
        employeeRepository.deleteById(idEmployee);
        return ResponseEntity.ok().build();
    }

}
