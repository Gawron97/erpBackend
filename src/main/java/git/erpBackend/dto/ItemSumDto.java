package git.erpBackend.dto;

import git.erpBackend.entity.ItemSum;
import lombok.Data;

import java.util.List;

@Data
public class ItemSumDto {

    private Integer id;
    private String name;
    private double quantity;
    private String quantityType;
    private List<String> warehouseNames;

    public static ItemSumDto of(ItemSum itemSum){
        ItemSumDto dto = new ItemSumDto();
        dto.id = itemSum.getId();
        dto.name = itemSum.getName();
        dto.quantity = itemSum.getQuantity();
        dto.quantityType = itemSum.getQuantityType().getQuantityType().toString();
        dto.warehouseNames = itemSum.getWarehouses().stream().map(warehouse -> warehouse.getName()).toList();

        return dto;
    }

}
