package git.erpBackend.controller;

import git.erpBackend.dto.OperatorLoginCredentialsDto;
import git.erpBackend.dto.OperatorRegisterCredentialsDto;
import git.erpBackend.entity.Operator;
import git.erpBackend.repository.OperatorRepository;
import git.erpBackend.service.Authenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorRepository operatorRepository;
    @Autowired
    private final Authenticator authenticator;

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

    @PostMapping("/verify_operator_login_credentials")
    public OperatorLoginCredentialsDto verifyOperatorLoginCredentials(
            @RequestBody OperatorLoginCredentialsDto operatorLoginCredentialsDto){

        return authenticator.loginAuthentication(operatorLoginCredentialsDto);
    }

    @PostMapping("/verify_operator_register_credentials")
    public OperatorRegisterCredentialsDto verifyOperatorRegisterCredentials(
            @RequestBody OperatorRegisterCredentialsDto operatorRegisterCredentialsDto){

        return authenticator.registerAuthentication(operatorRegisterCredentialsDto);
    }

}
