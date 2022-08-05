package git.erpBackend.dto;

import git.erpBackend.entity.Address;
import lombok.Data;

@Data
public class AddressDto {

    private Integer idAddress;
    private String city;
    private String street;
    private Integer streetNumber;
    private CountryDto countryDto;

    public static AddressDto of(Address address){
        AddressDto dto = new AddressDto();
        dto.idAddress = address.getIdAdress();
        dto.city = address.getCity();
        dto.street = address.getStreet();
        dto.streetNumber = address.getStreetNumber();
        dto.countryDto = CountryDto.of(address.getCountry());

        return dto;
    }

}
