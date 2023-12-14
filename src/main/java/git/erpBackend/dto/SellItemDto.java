package git.erpBackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SellItemDto {

    @NotNull
    private Integer idItem;
    @NotNull
    private double quantityToSell;
    @NotNull
    private Integer oldWarehouseId;
    @NotNull
    private Integer idTruck;

}
