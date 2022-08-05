package git.erpBackend.dto;

import git.erpBackend.entity.Warehouse;
import lombok.Data;

@Data
public class WarehouseDto {

    private Integer idWarehouse;
    private String name;
    private AddressDto addressDto;

    public static WarehouseDto of(Warehouse warehouse){
        WarehouseDto dto = new WarehouseDto();
        dto.idWarehouse = warehouse.getIdWarehouse();
        dto.name = warehouse.getName();
        dto.addressDto = AddressDto.of(warehouse.getAddress());

        return dto;
    }

}
