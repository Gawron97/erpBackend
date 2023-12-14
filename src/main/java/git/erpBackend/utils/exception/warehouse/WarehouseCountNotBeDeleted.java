package git.erpBackend.utils.exception.warehouse;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WarehouseCountNotBeDeleted extends RuntimeException {

    private final HttpStatus status;

    public WarehouseCountNotBeDeleted(){
        super("You cant delete this warehouse because there are some items inside");
        status = HttpStatus.BAD_REQUEST;
    }

    public WarehouseCountNotBeDeleted(String message){
        super(message);
        status = HttpStatus.BAD_REQUEST;
    }

}
