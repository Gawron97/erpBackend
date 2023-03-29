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

}
