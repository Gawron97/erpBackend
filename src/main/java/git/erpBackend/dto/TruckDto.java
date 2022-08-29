package git.erpBackend.dto;

import git.erpBackend.entity.Truck;
import lombok.Data;

@Data
public class TruckDto {

    private Integer idTruck;
    private String name;
    private double capacity;

    public static TruckDto of(Truck truck){
        TruckDto dto = new TruckDto();

        dto.idTruck = truck.getIdTruck();
        dto.name = truck.getName();
        dto.capacity = truck.getCapacity();

        return dto;
    }

}
