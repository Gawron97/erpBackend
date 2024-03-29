package git.erpBackend.utils.exception.truck;

import git.erpBackend.utils.exception.ErrorInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TruckExceptionHandler {

    @ExceptionHandler(TruckNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleTruckNotFoundException(TruckNotFoundException ex) {
        return ResponseEntity.status(ex.getStatus()).build();
    }

    @ExceptionHandler(TruckCapacityException.class)
    public ResponseEntity<ErrorInfo> handleTruckNotDoundException(TruckCapacityException ex) {
        return ResponseEntity.status(ex.getStatus()).build();
    }
}
