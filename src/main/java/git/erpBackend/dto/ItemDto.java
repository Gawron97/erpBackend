package git.erpBackend.dto;

import git.erpBackend.entity.Item;
import lombok.Data;

@Data
public class ItemDto {

    private Integer idItem;
    private String name;
    private double quantity;
    private QuantityTypeDto quantityTypeDto;
    private Integer idWarehouse;

    public static ItemDto of(Item item){
        ItemDto dto = new ItemDto();
        dto.idItem = item.getIdItem();
        dto.name = item.getName();
        dto.quantity = item.getQuantity();
        dto.quantityTypeDto = QuantityTypeDto.of(item.getQuantityType());
        dto.idWarehouse = item.getWarehouse().getIdWarehouse();

        return dto;
    }

}
