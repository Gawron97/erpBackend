package git.erpBackend.controller;

import git.erpBackend.entity.Item;
import git.erpBackend.entity.QuantityType;
import git.erpBackend.repository.ItemRepository;
import git.erpBackend.repository.QuantityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuantityTypeController {

    private final QuantityTypeRepository quantityTypeRepository;

    @PostMapping("/quantityType")
    public QuantityType newQuantityType(@RequestBody QuantityType quantityType){
        return quantityTypeRepository.save(quantityType);
    }

    @GetMapping("/quantityType")
    public List<QuantityType> listQuantityTypes(){
        return quantityTypeRepository.findAll();
    }

    @DeleteMapping("/quantityType")
    public ResponseEntity deteleQuantityType(@RequestBody Integer idQuantityType){
        quantityTypeRepository.deleteById(idQuantityType);
        return ResponseEntity.ok().build();
    }


}
