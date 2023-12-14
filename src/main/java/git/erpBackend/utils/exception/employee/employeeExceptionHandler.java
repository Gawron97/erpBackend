package git.erpBackend.utils.exception.employee;

import git.erpBackend.utils.exception.ErrorInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class employeeExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleEmployeeException(EmployeeNotFoundException ex){
        return ResponseEntity.status(ex.getStatus()).body(new ErrorInfo(ex.getMessage()));
    }

}
