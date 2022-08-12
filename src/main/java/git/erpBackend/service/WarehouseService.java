package git.erpBackend.service;

import git.erpBackend.dto.ItemDto;
import git.erpBackend.dto.WarehouseDto;
import git.erpBackend.entity.Item;
import git.erpBackend.entity.Warehouse;
import git.erpBackend.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    private WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository){
        this.warehouseRepository = warehouseRepository;
    }


    public WarehouseDto getWarehouse(Integer idWarehouse) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(idWarehouse);

        if(warehouseOptional.isPresent()){
            return WarehouseDto.of(warehouseOptional.get());
        }
        throw new RuntimeException("Nie znaleziono magazynu o id: " + idWarehouse);
    }

    public ResponseEntity deteleWarehouses(Integer idWarehouse){
        warehouseRepository.deleteById(idWarehouse);
        return ResponseEntity.ok().build();
    }

    public List<WarehouseDto> getListOfWarehouses(){
        return warehouseRepository.findAll().stream().map(warehouse -> WarehouseDto.of(warehouse)).collect(Collectors.toList());
    }

    public Warehouse newWarehouse(Warehouse warehouse){
        return warehouseRepository.save(warehouse);
    }

}
