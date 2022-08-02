package git.erpBackend.controller;

import git.erpBackend.dto.EmployeeDto;
import git.erpBackend.entity.Employee;
import git.erpBackend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public EmployeeDto newEmployee(@RequestBody EmployeeDto employeeDto){
        return EmployeeDto.of(employeeRepository.save(Employee.of(employeeDto)));
    }

    @GetMapping("/employees")
    public List<EmployeeDto> listEmployees(){
        return employeeRepository.findAll().stream().map(employee -> {
            return EmployeeDto.of(employee);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/employees")
    public ResponseEntity deleteEmployee(@RequestBody Integer idEmployee){
        employeeRepository.deleteById(idEmployee);
        return ResponseEntity.ok().build();
    }

}
