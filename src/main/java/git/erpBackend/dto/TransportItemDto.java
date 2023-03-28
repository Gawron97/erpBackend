package git.erpBackend.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class TransportItemDto {

    private Integer idItem;
    private double quantityToSend;
    private String transportationType;
    private Optional<Integer> newWarehouseId;
    private Integer idTruck;


}
