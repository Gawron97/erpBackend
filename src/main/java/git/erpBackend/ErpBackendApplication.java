package git.erpBackend;

import git.erpBackend.entity.Employee;
import git.erpBackend.repository.EmployeeRepository;
import git.erpBackend.service.StockItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ErpBackendApplication {

	@Autowired
	StockItemService stockItemService;

	public static void main(String[] args) {
		SpringApplication.run(ErpBackendApplication.class, args);

	}

}
