package git.erpBackend.controller;

import git.erpBackend.dto.WarehouseCBDto;
import git.erpBackend.dto.WarehouseDto;
import git.erpBackend.service.WarehouseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("/add")
    public ResponseEntity newWarehouse(@RequestBody WarehouseDto warehouse){
        return warehouseService.saveOrUpdateWarehouse(warehouse);
    }

    @GetMapping()
    public List<WarehouseDto> getListOfWarehouses(){
        return warehouseService.getListOfWarehouses();
    }

    @DeleteMapping("/delete/{idWarehouse}")
    public ResponseEntity deleteWarehouses(@PathVariable Integer idWarehouse){
        return warehouseService.deleteWarehouses(idWarehouse);
    }

    @GetMapping("{idWarehouse}")
    public WarehouseDto getWarehouse(@PathVariable Integer idWarehouse){
        return warehouseService.getWarehouse(idWarehouse);
    }

    @GetMapping("basic-info")
    public List<WarehouseCBDto> getWarehousesBasicInfo(){
        return warehouseService.getWarehousesCB();
    }

}
