package git.erpBackend.utils.exception.item;

import git.erpBackend.utils.exception.ErrorInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ItemExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleItemNotFoundException(ItemNotFoundException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorInfo(ex.getMessage()));
    }

    @ExceptionHandler(ItemSumNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleItemNotFoundException(ItemSumNotFoundException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorInfo(ex.getMessage()));
    }

    @ExceptionHandler(NotEnoughItemQuantityException.class)
    public ResponseEntity<ErrorInfo> handleNotEnoughItemQuantityException(NotEnoughItemQuantityException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorInfo(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateItemException.class)
    public ResponseEntity<ErrorInfo> handleDuplicateItemException(DuplicateItemException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ErrorInfo(ex.getMessage()));
    }

}
