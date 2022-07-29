package git.erpBackend.controller;

import git.erpBackend.entity.Employee;
import git.erpBackend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public Employee newEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees")
    public List<Employee> listEmployees(){
        return employeeRepository.findAll();
    }

    @DeleteMapping("/employees")
    public ResponseEntity deteleEmployee(@RequestBody Integer idEmployee){
        employeeRepository.deleteById(idEmployee);
        return ResponseEntity.ok().build();
    }

}
