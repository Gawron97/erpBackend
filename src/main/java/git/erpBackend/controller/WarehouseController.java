package git.erpBackend.controller;

import git.erpBackend.dto.WarehouseDto;
import git.erpBackend.entity.Warehouse;
import git.erpBackend.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;

    @PostMapping("/warehouses")
    public Warehouse newWarehouse(@RequestBody Warehouse warehouse){
        return warehouseRepository.save(warehouse);
    }

    @GetMapping("/warehouses")
    public List<WarehouseDto> listWarehouses(){
        return warehouseRepository.findAll().stream().map(warehouse -> WarehouseDto.of(warehouse)).collect(Collectors.toList());
    }

    @DeleteMapping("/warehouses")
    public ResponseEntity deteleWarehouses(@RequestBody Integer idWarehouse){
        warehouseRepository.deleteById(idWarehouse);
        return ResponseEntity.ok().build();
    }


}
