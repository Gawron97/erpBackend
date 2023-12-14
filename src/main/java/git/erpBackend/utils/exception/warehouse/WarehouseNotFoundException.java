package git.erpBackend.utils.exception.warehouse;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WarehouseNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public WarehouseNotFoundException(){
        super("warehouse not found");
        status = HttpStatus.NOT_FOUND;
    }

    public WarehouseNotFoundException(String message){
        super(message);
        status = HttpStatus.NOT_FOUND;
    }

}
