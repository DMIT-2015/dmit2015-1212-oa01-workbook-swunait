package dmit2015.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Immutable
@Table(name = "EMP_DETAILS_VIEW", schema = "HR", catalog = "")
public class EmpDetailsViewEntity {

    @Id
    @Basic
    @Column(name = "EMPLOYEE_ID", nullable = false, precision = 0)
    private Integer employeeId;
    @Basic
    @Column(name = "JOB_ID", nullable = false, length = 10)
    private String jobId;
    @Basic
    @Column(name = "MANAGER_ID", nullable = true, precision = 0)
    private Integer managerId;
    @Basic
    @Column(name = "DEPARTMENT_ID", nullable = true, precision = 0)
    private Short departmentId;
    @Basic
    @Column(name = "LOCATION_ID", nullable = true, precision = 0)
    private Short locationId;
    @Basic
    @Column(name = "COUNTRY_ID", nullable = true, length = 2)
    private String countryId;
    @Basic
    @Column(name = "FIRST_NAME", nullable = true, length = 20)
    private String firstName;
    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = 25)
    private String lastName;
    @Basic
    @Column(name = "SALARY", nullable = true, precision = 2)
    private Integer salary;
    @Basic
    @Column(name = "COMMISSION_PCT", nullable = true, precision = 2)
    private Byte commissionPct;
    @Basic
    @Column(name = "DEPARTMENT_NAME", nullable = false, length = 30)
    private String departmentName;
    @Basic
    @Column(name = "JOB_TITLE", nullable = false, length = 35)
    private String jobTitle;
    @Basic
    @Column(name = "CITY", nullable = false, length = 30)
    private String city;
    @Basic
    @Column(name = "STATE_PROVINCE", nullable = true, length = 25)
    private String stateProvince;
    @Basic
    @Column(name = "COUNTRY_NAME", nullable = true, length = 40)
    private String countryName;
    @Basic
    @Column(name = "REGION_NAME", nullable = true, length = 25)
    private String regionName;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Short getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Short departmentId) {
        this.departmentId = departmentId;
    }

    public Short getLocationId() {
        return locationId;
    }

    public void setLocationId(Short locationId) {
        this.locationId = locationId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Byte getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(Byte commissionPct) {
        this.commissionPct = commissionPct;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

        EmpDetailsViewEntity that = (EmpDetailsViewEntity) o;

        if (employeeId != null ? !employeeId.equals(that.employeeId) : that.employeeId != null) return false;
        if (jobId != null ? !jobId.equals(that.jobId) : that.jobId != null) return false;
        if (managerId != null ? !managerId.equals(that.managerId) : that.managerId != null) return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;
        if (locationId != null ? !locationId.equals(that.locationId) : that.locationId != null) return false;
        if (countryId != null ? !countryId.equals(that.countryId) : that.countryId != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (salary != null ? !salary.equals(that.salary) : that.salary != null) return false;
        if (commissionPct != null ? !commissionPct.equals(that.commissionPct) : that.commissionPct != null)
            return false;
        if (departmentName != null ? !departmentName.equals(that.departmentName) : that.departmentName != null)
            return false;
        if (jobTitle != null ? !jobTitle.equals(that.jobTitle) : that.jobTitle != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (stateProvince != null ? !stateProvince.equals(that.stateProvince) : that.stateProvince != null)
            return false;
        if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) return false;
        if (regionName != null ? !regionName.equals(that.regionName) : that.regionName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeId != null ? employeeId.hashCode() : 0;
        result = 31 * result + (jobId != null ? jobId.hashCode() : 0);
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (locationId != null ? locationId.hashCode() : 0);
        result = 31 * result + (countryId != null ? countryId.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (commissionPct != null ? commissionPct.hashCode() : 0);
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        result = 31 * result + (jobTitle != null ? jobTitle.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (stateProvince != null ? stateProvince.hashCode() : 0);
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        result = 31 * result + (regionName != null ? regionName.hashCode() : 0);
        return result;
    }
}
