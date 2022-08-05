package git.erpBackend.dto;

import git.erpBackend.entity.Country;
import lombok.Data;

@Data
public class CountryDto {

    private Integer idCountry;
    private String country;

    public static CountryDto of(Country country){
        CountryDto dto = new CountryDto();
        dto.idCountry = country.getIdCountry();
        dto.country = country.getName();

        return dto;
    }

}
