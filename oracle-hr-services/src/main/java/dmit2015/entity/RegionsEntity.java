package dmit2015.entity;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "REGIONS", schema = "HR", catalog = "")
public class RegionsEntity {
    @Id
    @Column(name = "REGION_ID", nullable = false, precision = 0)
    private BigInteger regionId;
    @Basic
    @Column(name = "REGION_NAME", nullable = true, length = 25)
    private String regionName;

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

        if (regionId != null ? !regionId.equals(that.regionId) : that.regionId != null) return false;
        if (regionName != null ? !regionName.equals(that.regionName) : that.regionName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = regionId != null ? regionId.hashCode() : 0;
        result = 31 * result + (regionName != null ? regionName.hashCode() : 0);
        return result;
    }
}
