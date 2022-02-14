package dmit2015.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "JOB_HISTORY", schema = "HR", catalog = "")
@IdClass(JobHistoryEntityPK.class)
public class JobHistoryEntity {

    @Id
    @Column(name = "EMPLOYEE_ID", nullable = false, precision = 0)
    private Integer employeeId;

    @Id
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;
    @Basic
    @Column(name = "END_DATE", nullable = false)
    private Date endDate;
    @Basic
    @Column(name = "JOB_ID", nullable = false, length = 10)
    private String jobId;
    @Basic
    @Column(name = "DEPARTMENT_ID", nullable = true, precision = 0)
    private Short departmentId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Short getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Short departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobHistoryEntity that = (JobHistoryEntity) o;

        if (employeeId != null ? !employeeId.equals(that.employeeId) : that.employeeId != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (jobId != null ? !jobId.equals(that.jobId) : that.jobId != null) return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeId != null ? employeeId.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (jobId != null ? jobId.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        return result;
    }
}
