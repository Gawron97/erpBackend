package git.erpBackend.utils.exception.truck;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TruckCapacityException extends RuntimeException{

    private final HttpStatus status;

    public TruckCapacityException() {
        super("Truck capacity is not enough");
        status = HttpStatus.BAD_REQUEST;
    }

    public TruckCapacityException(String message) {
        super(message);
        status = HttpStatus.BAD_REQUEST;
    }

}
