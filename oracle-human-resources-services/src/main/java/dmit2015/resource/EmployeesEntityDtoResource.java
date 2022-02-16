package dmit2015.resource;


import common.validator.BeanValidator;
import dmit2015.entity.EmployeesEntity;
import dmit2015.dto.EmployeesEntityDto;
import dmit2015.mapper.EmployeesEntityMapper;
import dmit2015.repository.EmployeesEntityRepository;
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
@Path("EmployeesEntityDtos")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class EmployeesEntityDtoResource {

    @Inject
    private EmployeesEntityRepository _employeesEntityRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response listEmployeesEntitys() {
        return Response.ok(
                _employeesEntityRepository
                        .list()
                        .stream()
                        .map(EmployeesEntityMapper.INSTANCE::toDto)
                        .collect(Collectors.toList())
        ).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findEmployeesEntityById(@PathParam("id") Long employeesEntityId) {
        EmployeesEntity existingEmployeesEntity = _employeesEntityRepository.findOptional(employeesEntityId).orElseThrow(NotFoundException::new);

        EmployeesEntityDto dto = EmployeesEntityMapper.INSTANCE.toDto(existingEmployeesEntity);

        return Response.ok(dto).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response addEmployeesEntity(EmployeesEntityDto dto, @Context UriInfo uriInfo) {
        EmployeesEntity newEmployeesEntity = EmployeesEntityMapper.INSTANCE.toEntity(dto);
        String errorMessage = BeanValidator.validateBean(EmployeesEntity.class, newEmployeesEntity);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            // Persist the new EmployeesEntity into the database
            _employeesEntityRepository.create(newEmployeesEntity);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // userInfo is injected via @Context parameter to this method
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(newEmployeesEntity.getEmployeeId() + "")
                .build();

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the EmployeesEntity was successfully persisted
        return Response
                .created(location)
                .build();
    }

    @PUT            // This method only accepts HTTP PUT requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response updateEmployeesEntity(@PathParam("id") Integer employeesEntityId, EmployeesEntityDto dto) {
        if (!employeesEntityId.equals(dto.getEmployeeId())) {
            throw new BadRequestException();
        }

        _employeesEntityRepository.findOptional(employeesEntityId).orElseThrow(NotFoundException::new);
        EmployeesEntity existingEmployeesEntity = EmployeesEntityMapper.INSTANCE.toEntity(dto);

        String errorMessage = BeanValidator.validateBean(EmployeesEntity.class, existingEmployeesEntity);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            _employeesEntityRepository.update(existingEmployeesEntity);
        } catch (Exception ex) {
            // Return an HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the EmployeesEntity was successfully persisted
        return Response.noContent().build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response delete(@PathParam("id") Integer employeesEntityId) {

        _employeesEntityRepository.findOptional(employeesEntityId).orElseThrow(NotFoundException::new);

        try {
            _employeesEntityRepository.delete(employeesEntityId);    // Removes the EmployeesEntity from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the EmployeesEntity was successfully deleted
        return Response.noContent().build();
    }

}

