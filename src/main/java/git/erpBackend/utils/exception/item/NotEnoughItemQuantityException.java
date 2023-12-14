package git.erpBackend.utils.exception.item;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotEnoughItemQuantityException extends RuntimeException {

    private final HttpStatus status;

    public NotEnoughItemQuantityException(){
        super("there is not enough item quantity in warehouse");
        status = HttpStatus.BAD_REQUEST;
    }

    public NotEnoughItemQuantityException(String message) {
        super(message);
        status = HttpStatus.BAD_REQUEST;
    }

}
