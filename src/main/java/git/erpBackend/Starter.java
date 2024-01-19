package git.erpBackend;

import git.erpBackend.entity.Employee;
import git.erpBackend.entity.Operator;
import git.erpBackend.enums.Role;
import git.erpBackend.repository.*;
import git.erpBackend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Starter implements CommandLineRunner {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    OperatorRepository operatorRepository;

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

    @Autowired
    TruckRepository truckRepository;

    @Autowired
    StockItemRepository stockItemRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

//        Operator operator = operatorRepository.findById(1).get();
//        operator.setPassword(passwordEncoder.encode("user"));
//        operatorRepository.save(operator);

//        Employee employee = new Employee();
//        employee.setName("Pawel");
//        employee.setSurname("Gruz");
//
//        Operator operator = new Operator();
//        operator.setUsername("user");
//        operator.setPassword(passwordEncoder.encode("1234"));
//        operator.setIsEnabled(true);
//        operator.setRole(Role.ADMIN);
//        operator.setEmployee(employee);
//
//        operatorRepository.save(operator);
//
//        System.out.println(employee);
//
//        Generator.generujEmployee(employeeRepository, positionRepository, 30);
//        Generator.generujWarehouses(warehouseRepository, countryRepository, 6);
//        Generator.generujItems(itemRepository, quantityTypeRepository, warehouseRepository, itemService, 50);
//        Generator.generujTrucks(truckRepository, 5);
//        Generator.generujStockItem(stockItemRepository, quantityTypeRepository, 4);

    }
}
