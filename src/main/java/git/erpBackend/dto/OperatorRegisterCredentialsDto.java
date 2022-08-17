package git.erpBackend.dto;

import git.erpBackend.entity.Operator;
import lombok.Data;

@Data
public class OperatorRegisterCredentialsDto {

    private String pesel;
    private String login;
    private String password;
    private Boolean authenticated;

}
