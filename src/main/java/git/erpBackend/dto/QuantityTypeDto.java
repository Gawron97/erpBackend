package git.erpBackend.dto;

import git.erpBackend.entity.QuantityType;
import lombok.Data;

@Data
public class QuantityTypeDto {

    private Integer idQuantityType;
    private String name;

    public static QuantityTypeDto of(QuantityType quantityType){
        QuantityTypeDto dto = new QuantityTypeDto();

        dto.idQuantityType = quantityType.getIdQuantityType();
        dto.name = quantityType.getQuantityType().name();

        return dto;
    }

}
