package git.erpBackend.dto;

import git.erpBackend.entity.Operator;
import lombok.Data;

@Data
public class OperatorAuthenticationResultDto {

    private Integer idOperator;
    private String name;
    private String surname;
    private boolean authenticated;

    public static OperatorAuthenticationResultDto createUnauthenticated(){
        OperatorAuthenticationResultDto dto = new OperatorAuthenticationResultDto();
        dto.authenticated = false;
        return dto;
    }

    public static OperatorAuthenticationResultDto of(Operator operator){
        OperatorAuthenticationResultDto dto = new OperatorAuthenticationResultDto();
        dto.idOperator = operator.getIdOperator();
        dto.name = operator.getEmployee().getName();
        dto.surname = operator.getEmployee().getSurname();
        dto.authenticated = true;
        return dto;
    }


}
