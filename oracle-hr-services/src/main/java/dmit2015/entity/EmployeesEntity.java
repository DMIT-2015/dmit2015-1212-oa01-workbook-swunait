package dmit2015.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "EMPLOYEES", schema = "HR", catalog = "")
public class EmployeesEntity {

    @Id
    @Column(name = "EMPLOYEE_ID", nullable = false, precision = 0)
    private Integer employeeId;
    @Basic
    @Column(name = "FIRST_NAME", nullable = true, length = 20)
    private String firstName;
    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = 25)
    private String lastName;
    @Basic
    @Column(name = "EMAIL", nullable = false, length = 25)
    private String email;
    @Basic
    @Column(name = "PHONE_NUMBER", nullable = true, length = 20)
    private String phoneNumber;
    @Basic
    @Column(name = "HIRE_DATE", nullable = false)
    private Date hireDate;
    @Basic
    @Column(name = "JOB_ID", nullable = false, length = 10)
    private String jobId;
    @Basic
    @Column(name = "SALARY", nullable = true, precision = 2)
    private Integer salary;
    @Basic
    @Column(name = "COMMISSION_PCT", nullable = true, precision = 2)
    private Byte commissionPct;
    @Basic
    @Column(name = "MANAGER_ID", nullable = true, precision = 0)
    private Integer managerId;
    @Basic
    @Column(name = "DEPARTMENT_ID", nullable = true, precision = 0)
    private Short departmentId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeesEntity that = (EmployeesEntity) o;

        if (employeeId != null ? !employeeId.equals(that.employeeId) : that.employeeId != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (hireDate != null ? !hireDate.equals(that.hireDate) : that.hireDate != null) return false;
        if (jobId != null ? !jobId.equals(that.jobId) : that.jobId != null) return false;
        if (salary != null ? !salary.equals(that.salary) : that.salary != null) return false;
        if (commissionPct != null ? !commissionPct.equals(that.commissionPct) : that.commissionPct != null)
            return false;
        if (managerId != null ? !managerId.equals(that.managerId) : that.managerId != null) return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeId != null ? employeeId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (hireDate != null ? hireDate.hashCode() : 0);
        result = 31 * result + (jobId != null ? jobId.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (commissionPct != null ? commissionPct.hashCode() : 0);
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        return result;
    }
}
