package git.erpBackend.controller;

import git.erpBackend.entity.Item;
import git.erpBackend.entity.Warehouse;
import git.erpBackend.repository.ItemRepository;
import git.erpBackend.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;

    @PostMapping("/warehouse")
    public Warehouse newWarehouse(@RequestBody Warehouse warehouse){
        return warehouseRepository.save(warehouse);
    }

    @GetMapping("/warehouse")
    public List<Warehouse> listWarehouses(){
        return warehouseRepository.findAll();
    }

    @DeleteMapping("/warehouse")
    public ResponseEntity deteleWarehouses(@RequestBody Integer idWarehouse){
        warehouseRepository.deleteById(idWarehouse);
        return ResponseEntity.ok().build();
    }


}
