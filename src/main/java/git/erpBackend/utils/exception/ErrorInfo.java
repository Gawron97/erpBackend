package git.erpBackend.utils.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class ErrorInfo {

    private final String message;

}
