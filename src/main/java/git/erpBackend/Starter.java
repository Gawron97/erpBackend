package git.erpBackend;

import git.erpBackend.entity.Employee;
import git.erpBackend.entity.Operator;
import git.erpBackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Starter implements CommandLineRunner {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
//
//        Operator operator = new Operator();
//        operator.setLogin("user");
//        operator.setPassword("1234");
//
//        Employee employee = new Employee();
//        employee.setName("Pawel");
//        employee.setSurname("Gruz");
//        employee.setOperator(operator);
//
//        employeeRepository.save(employee);
//
//        System.out.println(employee);

        Generator.generuj(employeeRepository);

    }
}
