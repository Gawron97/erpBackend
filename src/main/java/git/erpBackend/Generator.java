package git.erpBackend;

import com.github.javafaker.Faker;
import git.erpBackend.entity.*;
import git.erpBackend.enums.QuantityEnum;
import git.erpBackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Generator {

    private static Faker faker = new Faker();

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
            country.addAddress(address);

            warehouse.setAddress(address);

            warehouseRepository.save(warehouse);
        }

    }

    public static void generujItems(ItemRepository itemRepository, QuantityTypeRepository quantityTypeRepository, int ile){

        QuantityType kilo = new QuantityType();
        kilo.setQuantityType(QuantityEnum.KILOGRAMS);

        QuantityType unit = new QuantityType();
        unit.setQuantityType(QuantityEnum.UNIT);

        quantityTypeRepository.save(kilo);
        quantityTypeRepository.save(unit);


        for(int i=0; i<ile; i++){
            Item item = new Item();

            item.setName(faker.commerce().productName());
            item.setQuantity(faker.number().numberBetween(10, 100));
            int i1 = faker.number().numberBetween(1, 3);

            if(i1 == 2){
                item.setQuantityType(kilo);
                kilo.addItem(item);
            }else{
                item.setQuantityType(unit);
                unit.addItem(item);
            }


            itemRepository.save(item);

        }
    }

    public static void polaczItemsWithWarehouses(ItemRepository itemRepository, WarehouseRepository warehouseRepository,
                                                 int ile){



        for(int i=1; i<=6; i++){
            Optional<Warehouse> warehouse = warehouseRepository.findById(i);
            for(int j=0; j<ile; j++){
                Optional<Item> item = itemRepository.findById(faker.number().numberBetween(1, 49));
                warehouse.get().addItem(item.get());
                item.get().addWarehouse(warehouse.get());

                itemRepository.save(item.get());
            }
            warehouseRepository.save(warehouse.get());
        }


    }

}
