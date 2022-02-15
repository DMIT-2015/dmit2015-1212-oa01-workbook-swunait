package dmit2015.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "REGIONS", schema = "HR")
public class RegionsEntity {
    @Id
    @Column(name = "REGION_ID", nullable = false, precision = 0)
    private BigInteger regionId;
    @Basic
    @Column(name = "REGION_NAME", nullable = true, length = 25)
    private String regionName;
    @OneToMany(mappedBy = "regionsByRegionId")
    private Collection<CountriesEntity> countriesByRegionId;

    public BigInteger getRegionId() {
        return regionId;
    }

    public void setRegionId(BigInteger regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionsEntity that = (RegionsEntity) o;
        return Objects.equals(regionId, that.regionId) && Objects.equals(regionName, that.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, regionName);
    }

    public Collection<CountriesEntity> getCountriesByRegionId() {
        return countriesByRegionId;
    }

    public void setCountriesByRegionId(Collection<CountriesEntity> countriesByRegionId) {
        this.countriesByRegionId = countriesByRegionId;
    }
}
