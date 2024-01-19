package git.erpBackend.controller;

import git.erpBackend.dto.AuthenticationRequest;
import git.erpBackend.dto.AuthenticationResponse;
import git.erpBackend.dto.OperatorRegisterCredentialsDto;
import git.erpBackend.entity.Operator;
import git.erpBackend.repository.OperatorRepository;
import git.erpBackend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class OperatorController {

    private final OperatorRepository operatorRepository;
    private final AuthenticationService authenticationService;

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

        authenticationService.registerOperator(operatorRegisterCredentialsDto);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

}
