package git.erpBackend.utils.exception.quantityType;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class QuantityTypeNotFound extends RuntimeException {

    private final HttpStatus status;

    public QuantityTypeNotFound(){
        super("quantityType not found");
        status = HttpStatus.NOT_FOUND;
    }

    public QuantityTypeNotFound(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }

}
