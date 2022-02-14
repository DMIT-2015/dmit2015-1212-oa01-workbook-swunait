package dmit2015.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "LOCATIONS", schema = "HR", catalog = "")
public class LocationsEntity {
    @Id
    @Column(name = "LOCATION_ID", nullable = false, precision = 0)
    private Short locationId;
    @Basic
    @Column(name = "STREET_ADDRESS", nullable = true, length = 40)
    private String streetAddress;
    @Basic
    @Column(name = "POSTAL_CODE", nullable = true, length = 12)
    private String postalCode;
    @Basic
    @Column(name = "CITY", nullable = false, length = 30)
    private String city;
    @Basic
    @Column(name = "STATE_PROVINCE", nullable = true, length = 25)
    private String stateProvince;
    @Basic
    @Column(name = "COUNTRY_ID", nullable = true, length = 2)
    private String countryId;

    public Short getLocationId() {
        return locationId;
    }

    public void setLocationId(Short locationId) {
        this.locationId = locationId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationsEntity that = (LocationsEntity) o;

        if (locationId != null ? !locationId.equals(that.locationId) : that.locationId != null) return false;
        if (streetAddress != null ? !streetAddress.equals(that.streetAddress) : that.streetAddress != null)
            return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (stateProvince != null ? !stateProvince.equals(that.stateProvince) : that.stateProvince != null)
            return false;
        if (countryId != null ? !countryId.equals(that.countryId) : that.countryId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locationId != null ? locationId.hashCode() : 0;
        result = 31 * result + (streetAddress != null ? streetAddress.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (stateProvince != null ? stateProvince.hashCode() : 0);
        result = 31 * result + (countryId != null ? countryId.hashCode() : 0);
        return result;
    }
}
