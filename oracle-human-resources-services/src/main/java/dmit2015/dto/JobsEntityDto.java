package dmit2015.dto;

import lombok.Data;

@Data
public class JobsEntityDto {

    private String jobId;
    private String jobTitle;
    private Integer minSalary;
    private Integer maxSalary;

}
