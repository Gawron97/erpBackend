package git.erpBackend.controller;

import git.erpBackend.dto.OperatorAuthenticationResultDto;
import git.erpBackend.dto.OperatorCredentialsDto;
import git.erpBackend.entity.Operator;
import git.erpBackend.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorRepository operatorRepository;

    @PostMapping("/operator")
    public Operator newOperator(@RequestBody Operator operator){
        return operatorRepository.save(operator);
    }

    @GetMapping("/operator")
    public List<Operator> listOperators(){
        return operatorRepository.findAll();
    }

    @DeleteMapping("/operator")
    public ResponseEntity deteleOperator(@RequestBody Integer idOperator){
        operatorRepository.deleteById(idOperator);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify_operator_credentials")
    public OperatorAuthenticationResultDto verifyOperatorCredentials(@RequestBody OperatorCredentialsDto operatorCredentialsDto){

        Optional<Operator> optionalOperator = operatorRepository.findByLogin(operatorCredentialsDto.getLogin());
        if(optionalOperator.isEmpty()) {
            return OperatorAuthenticationResultDto.createUnauthenticated();
        }

        Operator operator = optionalOperator.get();

        if(!operator.getPassword().equals(operatorCredentialsDto.getPassword()))
            return OperatorAuthenticationResultDto.createUnauthenticated();
        else
            return OperatorAuthenticationResultDto.of(operator);


    }



}
