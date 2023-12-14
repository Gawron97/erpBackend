package git.erpBackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Optional;

@Data
public class TransportItemDto {

    @NotNull
    private Integer idItem;
    @NotNull
    private double quantityToSend;
    @NotNull
    private Integer oldWarehouseId;
    @NotNull
    private Integer newWarehouseId;
    @NotNull
    private Integer idTruck;

}
