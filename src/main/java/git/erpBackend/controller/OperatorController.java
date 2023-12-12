package git.erpBackend.controller;

import git.erpBackend.dto.OperatorRegisterCredentialsDto;
import git.erpBackend.entity.Operator;
import git.erpBackend.repository.OperatorRepository;
import git.erpBackend.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "user_password")
public class OperatorController {

    private final OperatorRepository operatorRepository;
    private final UserService userService;

    @GetMapping("/operator")
    public List<Operator> listOperators(){
        return operatorRepository.findAll();
    }

    @DeleteMapping("/operator")
    public ResponseEntity deteleOperator(@RequestBody Integer idOperator){
        operatorRepository.deleteById(idOperator);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public void verifyOperatorRegisterCredentials(
            @RequestBody OperatorRegisterCredentialsDto operatorRegisterCredentialsDto){

        userService.registerOperator(operatorRegisterCredentialsDto);
    }

}
