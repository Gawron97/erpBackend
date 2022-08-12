package git.erpBackend.dto;

import git.erpBackend.entity.Item;
import lombok.Data;

@Data
public class ItemDto {

    private Integer idItem;
    private String name;
    private double quantity;
    private String quantityType;
    private String warehouseName;

    public static ItemDto of(Item item){
        ItemDto dto = new ItemDto();
        dto.idItem = item.getIdItem();
        dto.name = item.getName();
        dto.quantity = item.getQuantity();
        dto.quantityType = item.getQuantityType().getQuantityType().toString();
        dto.warehouseName = item.getWarehouse().getName();

        return dto;
    }

}
