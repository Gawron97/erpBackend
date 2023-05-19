package git.erpBackend;

import com.github.javafaker.Faker;
import git.erpBackend.dto.ItemDto;
import git.erpBackend.dto.QuantityTypeDto;
import git.erpBackend.entity.*;
import git.erpBackend.enums.PositionEnum;
import git.erpBackend.enums.QuantityEnum;
import git.erpBackend.repository.*;
import git.erpBackend.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Generator {

    private static Faker faker = new Faker();
    private static List<Warehouse> warehouses = new ArrayList<>();

    public static void generujEmployee(EmployeeRepository employeeRepository, PositionRepository positionRepository, int ile){

        Position manager = new Position();
        manager.setPositionEnum(PositionEnum.MANAGER);

        Position regular_employee = new Position();
        regular_employee.setPositionEnum(PositionEnum.REGULAR_EMPLOYEE);

        positionRepository.save(manager);
        positionRepository.save(regular_employee);

        Employee employee2 = new Employee();
        employee2.setPesel("02223324467");
        employee2.setName("Jakub");
        employee2.setSurname("Gawron");
        employee2.setSalary("1000");
        employee2.setPosition(manager);
        employeeRepository.save(employee2);

        for(int i=0; i<ile; i++){
            Employee employee = new Employee();
            employee.setPesel(String.valueOf(faker.number().numberBetween(11111111111L, 99999999999L)));
            employee.setName(faker.name().firstName());
            employee.setSurname(faker.name().lastName());
            employee.setSalary(Integer.toString(faker.number().numberBetween(1000, 10000)));
            employee.setPosition(regular_employee);
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
                item.setQuantityTypeDto(QuantityTypeDto.of(kilo));
            }else{
                item.setQuantityTypeDto(QuantityTypeDto.of(unit));
            }

            Optional<Warehouse> warehouse = warehouseRepository.
                    findById(warehouses.get(faker.number().numberBetween(0, 6)).getIdWarehouse());

            item.setIdWarehouse(warehouse.get().getIdWarehouse());

            itemService.saveItem(item);

        }

    }

    public static void generujTrucks(TruckRepository truckRepository, int ile){

        for(int i=0; i<ile; i++){
            Truck truck = new Truck();
            truck.setName(faker.name().title());
            truck.setCapacity(faker.number().numberBetween(1, 100));

            truckRepository.save(truck);
        }
    }

    public static void generujStockItem(StockItemRepository stockItemRepository,
                                        QuantityTypeRepository quantityTypeRepository, int ile){

        Optional<QuantityType> QuantityType = quantityTypeRepository.findById(1);
        QuantityType quantityType = QuantityType.get();

        for(int i=0; i<ile; i++){
            StockItem stockItem = new StockItem();
            stockItem.setName(faker.commerce().productName());
            stockItem.setPrice(7);
            stockItem.setQuantityType(quantityType);

            stockItemRepository.save(stockItem);
        }

    }


}
