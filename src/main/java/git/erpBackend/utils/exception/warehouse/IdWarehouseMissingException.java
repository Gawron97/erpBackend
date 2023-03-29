package git.erpBackend.utils.exception.warehouse;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IdWarehouseMissingException extends RuntimeException {

    private final HttpStatus status;

    public IdWarehouseMissingException(){
        super("Id warehouse do not provided");
        status = HttpStatus.BAD_REQUEST;
    }

}
