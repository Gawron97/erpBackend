package git.erpBackend.dto;

import lombok.Data;

@Data
public class OperatorLoginCredentialsDto {

    private String login;
    private String password;
    private Boolean authenticated;

}
