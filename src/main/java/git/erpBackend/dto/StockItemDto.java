package git.erpBackend.dto;

import git.erpBackend.entity.QuantityType;
import git.erpBackend.entity.StockItem;
import lombok.Data;

@Data
public class StockItemDto {

    private Integer idStockItem;
    private String name;
    private double price;
    private QuantityTypeDto quantityTypeDto;

    public static StockItemDto of(StockItem stockItem){
        StockItemDto dto = new StockItemDto();
        dto.idStockItem = stockItem.getIdStockItem();
        dto.name = stockItem.getName();
        dto.price = stockItem.getPrice();
        dto.quantityTypeDto = QuantityTypeDto.of(stockItem.getQuantityType());

        return dto;
    }

}
