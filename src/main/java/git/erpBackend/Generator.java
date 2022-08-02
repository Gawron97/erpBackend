package git.erpBackend;

import com.github.javafaker.Faker;
import git.erpBackend.entity.Employee;
import git.erpBackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Generator {

    public static void generuj(EmployeeRepository employeeRepository){
        Faker faker = new Faker();

        for(int i=0; i<10; i++){
            Employee employee = new Employee();
            employee.setName(faker.name().firstName());
            employee.setSurname(faker.name().lastName());
            employee.setSalary(Integer.toString(faker.number().numberBetween(1000, 10000)));
            employeeRepository.save(employee);
        }
    }

}
