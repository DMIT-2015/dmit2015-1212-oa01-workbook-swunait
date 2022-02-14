package dmit2015.entity;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "COUNTRIES", schema = "HR", catalog = "")
public class CountriesEntity {

    @Id
    @Column(name = "COUNTRY_ID", nullable = false, length = 2)
    private String countryId;
    @Basic
    @Column(name = "COUNTRY_NAME", nullable = true, length = 40)
    private String countryName;
    @Basic
    @Column(name = "REGION_ID", nullable = true, precision = 0)
    private BigInteger regionId;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public BigInteger getRegionId() {
        return regionId;
    }

    public void setRegionId(BigInteger regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountriesEntity that = (CountriesEntity) o;

        if (countryId != null ? !countryId.equals(that.countryId) : that.countryId != null) return false;
        if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) return false;
        if (regionId != null ? !regionId.equals(that.regionId) : that.regionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = countryId != null ? countryId.hashCode() : 0;
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        result = 31 * result + (regionId != null ? regionId.hashCode() : 0);
        return result;
    }
}
