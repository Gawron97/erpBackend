package git.erpBackend;

import com.github.javafaker.Faker;
import git.erpBackend.dto.ItemDto;
import git.erpBackend.entity.*;
import git.erpBackend.enums.QuantityEnum;
import git.erpBackend.repository.*;
import git.erpBackend.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Generator {

    private static Faker faker = new Faker();
    private static List<Warehouse> warehouses = new ArrayList<>();

    public static void generujEmployee(EmployeeRepository employeeRepository, int ile){

        for(int i=0; i<ile; i++){
            Employee employee = new Employee();
            employee.setName(faker.name().firstName());
            employee.setSurname(faker.name().lastName());
            employee.setSalary(Integer.toString(faker.number().numberBetween(1000, 10000)));
            employeeRepository.save(employee);
        }
    }

    public static void generujWarehouses(WarehouseRepository warehouseRepository, CountryRepository countryRepository, int ile){

        List<Country> countryList = new ArrayList<>();

        for(int i=0; i<ile/2; i++){
            Country country = new Country();
            country.setName(faker.country().name());
            countryList.add(country);
            countryRepository.save(country);
        }

        for(int i=0; i<ile; i++){
            Warehouse warehouse = new Warehouse();
            warehouse.setName(faker.company().name());

            Address address = new Address();
            address.setCity(faker.address().city());
            address.setStreet(faker.address().streetName());
            address.setStreetNumber(Integer.parseInt(faker.address().streetAddressNumber()));

            Country country = countryList.get(faker.number().numberBetween(0, ile / 2));

            address.setCountry(country);

            warehouse.setAddress(address);

            warehouses.add(warehouse);

            warehouseRepository.save(warehouse);
        }

    }

    public static void generujItems(ItemRepository itemRepository, QuantityTypeRepository quantityTypeRepository,
                                    WarehouseRepository warehouseRepository, ItemService itemService, int ile){

        QuantityType kilo = new QuantityType();
        kilo.setQuantityType(QuantityEnum.KILOGRAMS);

        QuantityType unit = new QuantityType();
        unit.setQuantityType(QuantityEnum.UNIT);

        quantityTypeRepository.save(kilo);
        quantityTypeRepository.save(unit);


        for(int i=0; i<ile; i++){
            ItemDto item = new ItemDto();

            item.setName(faker.commerce().productName());
            item.setQuantity(faker.number().numberBetween(10, 100));
            int i1 = faker.number().numberBetween(1, 3);

            if(i1 == 2){
                item.setQuantityType(kilo.getQuantityType().toString());
            }else{
                item.setQuantityType(unit.getQuantityType().toString());
            }

            Optional<Warehouse> warehouse = warehouseRepository.
                    findByNameWithItems(warehouses.get(faker.number().numberBetween(1, 6)).getName());

            item.setWarehouseName(warehouse.get().getName());

            itemService.saveItem(item);

        }


    }


}
