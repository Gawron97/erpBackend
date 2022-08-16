package git.erpBackend.controller;

import git.erpBackend.dto.QuantityTypeDto;
import git.erpBackend.entity.Item;
import git.erpBackend.entity.QuantityType;
import git.erpBackend.repository.ItemRepository;
import git.erpBackend.repository.QuantityTypeRepository;
import git.erpBackend.service.QuantityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuantityTypeController {

    private final QuantityTypeRepository quantityTypeRepository;
    private final QuantityTypeService quantityTypeService;

    @PostMapping("/quantity_types")
    public QuantityType newQuantityType(@RequestBody QuantityType quantityType){
        return quantityTypeRepository.save(quantityType);
    }

    @GetMapping("/quantity_types")
    public List<QuantityTypeDto> listQuantityTypes(){
        return quantityTypeService.getQuantityTypeList();
    }

    @DeleteMapping("/quantity_types")
    public ResponseEntity deteleQuantityType(@RequestBody Integer idQuantityType){
        quantityTypeRepository.deleteById(idQuantityType);
        return ResponseEntity.ok().build();
    }


}
