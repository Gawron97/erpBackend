package git.erpBackend.controller;

import git.erpBackend.dto.WarehouseCBDto;
import git.erpBackend.dto.WarehouseDto;
import git.erpBackend.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("/warehouses")
    public ResponseEntity newWarehouse(@RequestBody WarehouseDto warehouse){
        return warehouseService.saveOrUpdateWarehouse(warehouse);
    }

    @GetMapping("/warehouses")
    public List<WarehouseDto> getListOfWarehouses(){
        return warehouseService.getListOfWarehouses();
    }

    @DeleteMapping("/warehouses/{idWarehouse}")
    public ResponseEntity deteleWarehouses(@PathVariable Integer idWarehouse){
        return warehouseService.deleteWarehouses(idWarehouse);
    }

    @GetMapping("/warehouse/details/{idWarehouse}")
    public WarehouseDto getWarehouseDetails(@PathVariable Integer idWarehouse){
        return warehouseService.getWarehouseDetails(idWarehouse);
    }

    @GetMapping("/warehouses/{idWarehouse}")
    public WarehouseDto getWarehouseWithItems(@PathVariable Integer idWarehouse){
        return warehouseService.getWarehouseWithItems(idWarehouse);
    }

    @GetMapping("/warehouses_cb")
    public List<WarehouseCBDto> getWarehousesCB(){
        return warehouseService.getWarehousesCB();
    }

}
