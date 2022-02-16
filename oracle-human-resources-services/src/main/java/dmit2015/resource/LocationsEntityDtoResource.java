package dmit2015.resource;
import common.validator.BeanValidator;
import dmit2015.entity.LocationsEntity;
import dmit2015.dto.LocationsEntityDto;
import dmit2015.mapper.LocationsEntityMapper;
import dmit2015.repository.LocationsEntityRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("LocationsEntityDtos")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class LocationsEntityDtoResource {

    @Inject
    private LocationsEntityRepository _locationsEntityRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response listLocationsEntitys() {
        return Response.ok(
                _locationsEntityRepository
                        .list()
                        .stream()
                        .map(LocationsEntityMapper.INSTANCE::toDto)
                        .collect(Collectors.toList())
        ).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findLocationsEntityById(@PathParam("id") Long locationsEntityId) {
        LocationsEntity existingLocationsEntity = _locationsEntityRepository.findOptional(locationsEntityId).orElseThrow(NotFoundException::new);

        LocationsEntityDto dto = LocationsEntityMapper.INSTANCE.toDto(existingLocationsEntity);

        return Response.ok(dto).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response addLocationsEntity(LocationsEntityDto dto, @Context UriInfo uriInfo) {
        LocationsEntity newLocationsEntity = LocationsEntityMapper.INSTANCE.toEntity(dto);
        String errorMessage = BeanValidator.validateBean(LocationsEntity.class, newLocationsEntity);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            // Persist the new LocationsEntity into the database
            _locationsEntityRepository.create(newLocationsEntity);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // userInfo is injected via @Context parameter to this method
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(newLocationsEntity.getLocationId() + "")
                .build();

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the LocationsEntity was successfully persisted
        return Response
                .created(location)
                .build();
    }

    @PUT            // This method only accepts HTTP PUT requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response updateLocationsEntity(@PathParam("id") Long locationsEntityId, LocationsEntityDto dto) {
        if (!locationsEntityId.equals(dto.getLocationId())) {
            throw new BadRequestException();
        }

        _locationsEntityRepository.findOptional(locationsEntityId).orElseThrow(NotFoundException::new);
        LocationsEntity existingLocationsEntity = LocationsEntityMapper.INSTANCE.toEntity(dto);

        String errorMessage = BeanValidator.validateBean(LocationsEntity.class, existingLocationsEntity);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            _locationsEntityRepository.update(existingLocationsEntity);
        } catch (Exception ex) {
            // Return an HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the LocationsEntity was successfully persisted
        return Response.noContent().build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response delete(@PathParam("id") Long locationsEntityId) {

        _locationsEntityRepository.findOptional(locationsEntityId).orElseThrow(NotFoundException::new);

        try {
            _locationsEntityRepository.delete(locationsEntityId);    // Removes the LocationsEntity from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the LocationsEntity was successfully deleted
        return Response.noContent().build();
    }

}

