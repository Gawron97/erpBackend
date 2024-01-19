package git.erpBackend.service;

import git.erpBackend.dto.AuthenticationRequest;
import git.erpBackend.dto.AuthenticationResponse;
import git.erpBackend.dto.OperatorRegisterCredentialsDto;
import git.erpBackend.entity.Employee;
import git.erpBackend.entity.Operator;
import git.erpBackend.enums.Role;
import git.erpBackend.repository.EmployeeRepository;
import git.erpBackend.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final OperatorRepository operatorRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    Employee findEmployeeByPesel(String pesel) {
        return employeeRepository.findByPesel(pesel).orElseThrow(() -> new RuntimeException("Employee with that pesel not found"));
    }

    public void registerOperator(OperatorRegisterCredentialsDto registerCredentialsDto) {
        Employee employee = findEmployeeByPesel(registerCredentialsDto.getPesel());

        Operator operator = Operator.builder()
                .username(registerCredentialsDto.getUsername())
                .password(passwordEncoder.encode(registerCredentialsDto.getPassword()))
                .role(Role.ADMIN)
                .employee(employee)
                .isEnabled(true)
                .build();
        operatorRepository.save(operator);

    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        );

        return new AuthenticationResponse(jwtService.generateToken(authentication.getName(), authentication.getAuthorities()));

    }

}
