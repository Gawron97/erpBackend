package git.erpBackend.utils.exception.warehouse;

import git.erpBackend.utils.exception.ErrorInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WarehouseExceptionHandler {

    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleWarehouseNotFoundException(WarehouseNotFoundException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorInfo(ex.getMessage()));
    }

    @ExceptionHandler(WarehouseCountNotBeDeleted.class)
    public ResponseEntity<ErrorInfo> handleWarehouseCouldNotDeleteException(WarehouseCountNotBeDeleted ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorInfo(ex.getMessage()));
    }

    @ExceptionHandler(IdWarehouseMissingException.class)
    public ResponseEntity<ErrorInfo> handleIdWarehouseMissingException(IdWarehouseMissingException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorInfo(ex.getMessage()));
    }

}
