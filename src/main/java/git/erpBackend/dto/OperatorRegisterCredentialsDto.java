package git.erpBackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OperatorRegisterCredentialsDto {

    @NotBlank
    private String pesel;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
