package git.erpBackend.dto;

import lombok.Data;

@Data
public class TransportItemDto {

    private Integer idItem;
    private double quantityToSend;
    private String transportationType;
    private Integer newWarehouseId;
    private Integer idTruck;


}
