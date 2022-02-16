package dmit2015.mapper;

import dmit2015.dto.LocationsEntityDto;
import dmit2015.entity.LocationsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationsEntityMapper {

    LocationsEntityMapper INSTANCE = Mappers.getMapper(LocationsEntityMapper.class);

    @Mappings({
            @Mapping(target = "countryName", source = "entity.countriesByCountryId.countryName")
    })
    LocationsEntityDto toDto(LocationsEntity entity);

//    @Mappings({
//            @Mapping(target = "id", source = "locationsentityId")
//    })
    LocationsEntity toEntity(LocationsEntityDto dto);

}

