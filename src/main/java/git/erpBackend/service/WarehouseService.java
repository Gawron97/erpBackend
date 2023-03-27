package git.erpBackend.service;

import git.erpBackend.dto.WarehouseCBDto;
import git.erpBackend.dto.WarehouseDto;
import git.erpBackend.entity.Address;
import git.erpBackend.entity.Country;
import git.erpBackend.entity.ItemSum;
import git.erpBackend.entity.Warehouse;
import git.erpBackend.repository.CountryRepository;
import git.erpBackend.repository.WarehouseRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WarehouseService {

    private WarehouseRepository warehouseRepository;
    private CountryRepository countryRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, CountryRepository countryRepository){
        this.warehouseRepository = warehouseRepository;
        this.countryRepository = countryRepository;
    }


    public WarehouseDto getWarehouseDetails(Integer idWarehouse) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(idWarehouse);

        if(warehouseOptional.isPresent()){
            return WarehouseDto.of(warehouseOptional.get());
        }
        throw new RuntimeException("Nie znaleziono magazynu o id: " + idWarehouse);
    }

    public WarehouseDto getWarehouseWithItems(Integer idWarehouse) {

        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(idWarehouse);

        if(warehouseOptional.isPresent()){
            return WarehouseDto.of(warehouseOptional.get());
        }
        throw new RuntimeException("Nie znaleziono magazynu o id: " + idWarehouse);

    }

    public ResponseEntity deleteWarehouses(Integer idWarehouse){

        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(idWarehouse);
        Warehouse warehouse = warehouseOptional.orElseThrow(() -> new RuntimeException("brak takiego magazynu"));
        if(warehouse.getItems().size() > 0) {
            throw new RuntimeException("nie mozna usunać magazynu, poniewaz przechowuje produkty");
        }
        warehouseRepository.deleteById(idWarehouse);
        return ResponseEntity.ok().build();
    }

    public List<WarehouseDto> getListOfWarehouses(){
        return warehouseRepository.findAll().stream().map(warehouse -> WarehouseDto.of(warehouse)).collect(Collectors.toList());
    }

    public ResponseEntity saveOrUpdateWarehouse(WarehouseDto warehouseDto) {

        Optional<Country> optionalCountry = countryRepository.findByName(warehouseDto.getAddressDto().
                getCountryDto().getCountry());
        Country country = optionalCountry.orElseGet(() -> new Country());
        country.setName(warehouseDto.getAddressDto().getCountryDto().getCountry());
        countryRepository.save(country);

        Address address = Address.of(warehouseDto.getAddressDto().getCity(), warehouseDto.getAddressDto().getStreet(),
                warehouseDto.getAddressDto().getStreetNumber(), country);

        Warehouse warehouse = new Warehouse(warehouseDto.getName(), address);

        if(warehouseDto.getIdWarehouse() != null) {
            warehouse.setIdWarehouse(warehouseDto.getIdWarehouse());
            address.setIdAdress(warehouseDto.getAddressDto().getIdAddress());
        }

        warehouseRepository.save(warehouse);
        return ResponseEntity.ok().build();

    }

    public List<WarehouseCBDto> getWarehousesCB() {

        return warehouseRepository.findAll().stream().map(warehouse -> WarehouseCBDto.of(warehouse)).toList();

    }

}
