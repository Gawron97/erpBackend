package git.erpBackend.utils.exception.truck;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TruckNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public TruckNotFoundException(){
        super("truck not found");
        status = HttpStatus.NOT_FOUND;
    }

}
