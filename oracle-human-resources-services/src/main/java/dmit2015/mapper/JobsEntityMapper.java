package dmit2015.mapper;

import dmit2015.dto.JobsEntityDto;
import dmit2015.entity.JobsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobsEntityMapper {

    JobsEntityMapper INSTANCE = Mappers.getMapper(JobsEntityMapper.class);

//    @Mappings({
//            @Mapping(target = "jobsentityId", source = "entity.id")
//    })
    JobsEntityDto toDto(JobsEntity entity);

//    @Mappings({
//            @Mapping(target = "id", source = "jobsentityId")
//    })
    JobsEntity toEntity(JobsEntityDto dto);

}

