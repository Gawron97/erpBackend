package git.erpBackend;

import git.erpBackend.entity.Employee;
import git.erpBackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErpBackendApplication {


	public static void main(String[] args) {
		SpringApplication.run(ErpBackendApplication.class, args);


	}


}
