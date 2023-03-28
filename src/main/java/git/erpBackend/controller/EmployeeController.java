package git.erpBackend.controller;

import git.erpBackend.dto.EmployeeDto;
import git.erpBackend.entity.Employee;
import git.erpBackend.repository.EmployeeRepository;
import git.erpBackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity saveOrUpdateEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.saveOrUpdateEmployee(employeeDto);
    }

    @GetMapping("/employees")
    public List<EmployeeDto> listEmployees(){
        return employeeService.getEmployeeList();
    }

    @GetMapping("/employees/{idEmployee}")
    public EmployeeDto getEmployee(@PathVariable Integer idEmployee) {
        return employeeService.getEmployee(idEmployee);
    }

    @DeleteMapping("/employees/{idEmployee}")
    public ResponseEntity deleteEmployee(@PathVariable Integer idEmployee){
        return employeeService.deleteEmployee(idEmployee);
    }

}
