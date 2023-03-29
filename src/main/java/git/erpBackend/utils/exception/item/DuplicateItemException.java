package git.erpBackend.utils.exception.item;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateItemException extends RuntimeException {

    private final HttpStatus status;

    public DuplicateItemException(){
        super("there is already this item in this warehouse");
        status = HttpStatus.BAD_REQUEST;
    }

}
