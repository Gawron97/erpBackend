package git.erpBackend.utils.exception.item;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ItemNotExistsInWarehouseException extends RuntimeException {

    private final HttpStatus status;

    public ItemNotExistsInWarehouseException() {
        super("Item not exists in warehouse");
        status = HttpStatus.BAD_REQUEST;
    }

    public ItemNotExistsInWarehouseException(String message) {
        super(message);
        status = HttpStatus.BAD_REQUEST;
    }


}
