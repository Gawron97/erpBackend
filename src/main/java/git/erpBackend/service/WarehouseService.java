package git.erpBackend.service;

import git.erpBackend.dto.WarehouseCBDto;
import git.erpBackend.dto.WarehouseDto;
import git.erpBackend.entity.Address;
import git.erpBackend.entity.Country;
import git.erpBackend.entity.Warehouse;
import git.erpBackend.repository.CountryRepository;
import git.erpBackend.repository.WarehouseRepository;
import git.erpBackend.utils.exception.warehouse.WarehouseCountNotBeDeleted;
import git.erpBackend.utils.exception.warehouse.WarehouseNotFoundException;
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

    public WarehouseDto getWarehouse(Integer idWarehouse) {

        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(idWarehouse);
        Warehouse warehouse = warehouseOptional.orElseThrow(() -> {
            throw new WarehouseNotFoundException();
        });
        return WarehouseDto.of(warehouse);

    }

    public ResponseEntity deleteWarehouses(Integer idWarehouse){

        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(idWarehouse);
        Warehouse warehouse = warehouseOptional.orElseThrow(() -> new WarehouseNotFoundException());
        if(warehouse.getItems().size() > 0) {
            throw new WarehouseCountNotBeDeleted();
        }
        warehouseRepository.deleteById(idWarehouse);
        return ResponseEntity.ok().build();
    }

    public List<WarehouseDto> getListOfWarehouses(){
        throw new WarehouseNotFoundException();
//        return warehouseRepository.findAll().stream().map(warehouse -> WarehouseDto.of(warehouse)).collect(Collectors.toList());
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
