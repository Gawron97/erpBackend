package git.erpBackend.utils.exception.item;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ItemSumNotFoundException extends RuntimeException{

    private final HttpStatus status;

    public ItemSumNotFoundException() {
        super("ItemSum not found");
        status = HttpStatus.NOT_FOUND;
    }

    public ItemSumNotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }

}
