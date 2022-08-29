package git.erpBackend.controller;

import git.erpBackend.dto.ItemDto;
import git.erpBackend.dto.WarehouseCBDto;
import git.erpBackend.dto.WarehouseDto;
import git.erpBackend.entity.Warehouse;
import git.erpBackend.repository.WarehouseRepository;
import git.erpBackend.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("/warehouses")
    public Warehouse newWarehouse(@RequestBody Warehouse warehouse){
        return warehouseService.newWarehouse(warehouse);
    }

    @GetMapping("/warehouses")
    public List<WarehouseDto> getListOfWarehouses(){
        return warehouseService.getListOfWarehouses();
    }

    @DeleteMapping("/warehouses")
    public ResponseEntity deteleWarehouses(@RequestBody Integer idWarehouse){
        return warehouseService.deteleWarehouses(idWarehouse);
    }

    @GetMapping("/warehouses/{idWarehouse}")
    public WarehouseDto getWarehouseWithItems(@PathVariable Integer idWarehouse){
        return warehouseService.getWarehouse(idWarehouse);
    }

    @GetMapping("/warehouses_cb")
    public List<WarehouseCBDto> getWarehousesCB(){
        return warehouseService.getWarehousesCB();
    }

}
