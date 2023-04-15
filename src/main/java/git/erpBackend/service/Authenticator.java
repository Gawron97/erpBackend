package git.erpBackend.service;

import git.erpBackend.dto.OperatorLoginCredentialsDto;
import git.erpBackend.dto.OperatorRegisterCredentialsDto;
import git.erpBackend.entity.Employee;
import git.erpBackend.entity.Operator;
import git.erpBackend.repository.EmployeeRepository;
import git.erpBackend.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Authenticator {

    private final OperatorRepository operatorRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Authenticator(OperatorRepository operatorRepository, EmployeeRepository employeeRepository){
        this.operatorRepository = operatorRepository;
        this.employeeRepository = employeeRepository;
        passwordEncoder = new BCryptPasswordEncoder();
    }


    public OperatorLoginCredentialsDto loginAuthentication(OperatorLoginCredentialsDto loginCredentialsDto){
        Optional<Operator> optionalOperator = operatorRepository.findByLogin(loginCredentialsDto.getLogin());
        if(optionalOperator.isEmpty()) {
            return loginCredentialsDto;
        }

        Operator operator = optionalOperator.get();

        if(!operator.getPassword().equals(loginCredentialsDto.getPassword()))
            return loginCredentialsDto;

        loginCredentialsDto.setAuthenticated(true);
        return loginCredentialsDto;
    }

    public OperatorRegisterCredentialsDto registerAuthentication(OperatorRegisterCredentialsDto registerCredentialsDto){
        Optional<Employee> employeeOptional = employeeRepository.findByPesel(registerCredentialsDto.getPesel());

        if(employeeOptional.isEmpty())
            return registerCredentialsDto;

        Employee employee = employeeOptional.get();

        if(!employee.getPosition().getPositionEnum().name().equalsIgnoreCase("MANAGER"))
            return registerCredentialsDto;

        registerCredentialsDto.setAuthenticated(true);

        Operator operator = Operator.of(registerCredentialsDto.getLogin(),
                passwordEncoder.encode(registerCredentialsDto.getPassword()), employee);

        operatorRepository.save(operator);

        return registerCredentialsDto;

    }

}
