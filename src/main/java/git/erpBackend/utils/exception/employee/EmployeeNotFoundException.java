package git.erpBackend.utils.exception.employee;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmployeeNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public EmployeeNotFoundException(){
        super("employee not found");
        status = HttpStatus.NOT_FOUND;
    }

    public EmployeeNotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }

}
