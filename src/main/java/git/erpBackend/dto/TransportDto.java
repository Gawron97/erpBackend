package git.erpBackend.dto;

import git.erpBackend.entity.Item;
import git.erpBackend.entity.Truck;
import lombok.Data;

import java.util.List;

@Data
public class TransportDto {

    private Integer idItem;
    private String name;
    private String warehouseName;
    private double quantity;
    private QuantityTypeDto quantityTypeDto;
    private List<TruckDto> truckDtoList;

    public static TransportDto of(Item item, List<Truck> trucks){

        TransportDto dto = new TransportDto();

        dto.idItem = item.getIdItem();
        dto.name = item.getName();
        dto.warehouseName = item.getWarehouse().getName();
        dto.quantity = item.getQuantity();
        dto.quantityTypeDto = QuantityTypeDto.of(item.getQuantityType());
        dto.truckDtoList = trucks.stream().map(truck -> TruckDto.of(truck)).toList();

        return dto;
    }

}
