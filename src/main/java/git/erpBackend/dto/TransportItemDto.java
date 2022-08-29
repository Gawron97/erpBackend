package git.erpBackend.dto;

import lombok.Data;

@Data
public class TransportItemDto {

    private Integer id;
    private String name;
    private double oldQuantity;
    private double newQuantity;
    private QuantityTypeDto quantityTypeDto;
    private String transportationType;
    private Integer oldWarehouseId;
    private Integer newWarehouseId;


}
