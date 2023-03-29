package git.erpBackend.utils.exception.item;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ItemNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public ItemNotFoundException(){
        super("Item not found");
        status = HttpStatus.NOT_FOUND;
    }

}
