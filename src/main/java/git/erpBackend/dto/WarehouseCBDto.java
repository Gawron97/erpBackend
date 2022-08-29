package git.erpBackend.dto;

import git.erpBackend.entity.Warehouse;
import lombok.Data;

@Data
public class WarehouseCBDto {

    private Integer idWarehouse;
    private String name;

    public static WarehouseCBDto of(Warehouse warehouse){
        WarehouseCBDto dto = new WarehouseCBDto();

        dto.idWarehouse = warehouse.getIdWarehouse();
        dto.name = warehouse.getName();

        return dto;
    }

}
