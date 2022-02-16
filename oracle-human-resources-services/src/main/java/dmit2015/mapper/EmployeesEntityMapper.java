package dmit2015.mapper;

import dmit2015.dto.EmployeesEntityDto;
import dmit2015.entity.EmployeesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeesEntityMapper {

    EmployeesEntityMapper INSTANCE = Mappers.getMapper(EmployeesEntityMapper.class);

    @Mappings({
            @Mapping(target = "jobTitle", source = "entity.jobsByJobId.jobTitle"),
            @Mapping(target = "departmentName", source = "entity.departmentsByDepartmentId.departmentName"),
            @Mapping(target = "managerName", source = "entity.employeesByManagerId.fullName")
    })
    EmployeesEntityDto toDto(EmployeesEntity entity);

//    @Mappings({
//            @Mapping(target = "id", source = "employeesentityId")
//    })
    EmployeesEntity toEntity(EmployeesEntityDto dto);

}

