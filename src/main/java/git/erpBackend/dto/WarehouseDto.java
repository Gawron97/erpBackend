package git.erpBackend.dto;

import git.erpBackend.entity.Warehouse;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class WarehouseDto {

    private Integer idWarehouse;
    private String name;
    private AddressDto addressDto;
    private List<ItemDto> items;

    public static WarehouseDto of(Warehouse warehouse){
        WarehouseDto dto = new WarehouseDto();
        dto.idWarehouse = warehouse.getIdWarehouse();
        dto.name = warehouse.getName();
        dto.addressDto = AddressDto.of(warehouse.getAddress());
        dto.items = warehouse.getItems().stream().map(item -> ItemDto.of(item)).collect(Collectors.toList());

        return dto;
    }

}
