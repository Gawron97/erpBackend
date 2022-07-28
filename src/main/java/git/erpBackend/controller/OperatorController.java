package git.erpBackend.controller;

import git.erpBackend.entity.Item;
import git.erpBackend.entity.Operator;
import git.erpBackend.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorRepository operatorRepository;

    @PostMapping("/operator")
    Operator newOperator(@RequestBody Operator operator){
        return operatorRepository.save(operator);
    }

    @GetMapping("/operator")
    List<Operator> listOperators(){
        return operatorRepository.findAll();
    }

    @DeleteMapping("/operator")
    ResponseEntity deteleOperator(@RequestBody Integer idOperator){
        operatorRepository.deleteById(idOperator);
        return ResponseEntity.ok().build();
    }

}
