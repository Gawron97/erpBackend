package git.erpBackend.utils.exception.quantityType;

import git.erpBackend.utils.exception.ErrorInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuantityTypeExceptionHandler {

    @ExceptionHandler(QuantityTypeNotFound.class)
    public ResponseEntity<ErrorInfo> handleQuantityNotFoundException(QuantityTypeNotFound ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorInfo(ex.getMessage()));
    }

}
