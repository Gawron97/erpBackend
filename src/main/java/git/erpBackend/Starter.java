package git.erpBackend;

import git.erpBackend.entity.Employee;
import git.erpBackend.entity.ItemSum;
import git.erpBackend.entity.Operator;
import git.erpBackend.entity.QuantityType;
import git.erpBackend.repository.*;
import git.erpBackend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Starter implements CommandLineRunner {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    QuantityTypeRepository quantityTypeRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    PositionRepository positionRepository;

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

        Generator.generujEmployee(employeeRepository, positionRepository, 30);
        Generator.generujWarehouses(warehouseRepository, countryRepository, 6);
        Generator.generujItems(itemRepository, quantityTypeRepository, warehouseRepository, itemService, 50);


    }
}
