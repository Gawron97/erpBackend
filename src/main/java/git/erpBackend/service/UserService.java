package git.erpBackend.service;

import git.erpBackend.dto.OperatorRegisterCredentialsDto;
import git.erpBackend.entity.Employee;
import git.erpBackend.entity.Operator;
import git.erpBackend.enums.Role;
import git.erpBackend.repository.EmployeeRepository;
import git.erpBackend.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final OperatorRepository operatorRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return operatorRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

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
}
