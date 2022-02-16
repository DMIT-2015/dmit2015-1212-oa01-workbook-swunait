package dmit2015.dto;

import lombok.Data;

@Data
public class LocationsEntityDto {

    private Short locationId;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;

    private String countryName;

}
