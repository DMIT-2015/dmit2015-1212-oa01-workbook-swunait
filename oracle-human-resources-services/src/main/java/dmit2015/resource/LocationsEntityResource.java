package dmit2015.resource;


import common.validator.BeanValidator;
import dmit2015.entity.LocationsEntity;
import dmit2015.repository.LocationsEntityRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@ApplicationScoped
@Path("LocationsEntitys")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class LocationsEntityResource {

    @Inject
    private LocationsEntityRepository _locationsEntityRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response listLocationsEntitys() {
        return Response.ok(_locationsEntityRepository.list()).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findLocationsEntityById(@PathParam("id") Long locationsEntityId) {
        LocationsEntity existingLocationsEntity = _locationsEntityRepository.findOptional(locationsEntityId).orElseThrow(NotFoundException::new);

        return Response.ok(existingLocationsEntity).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response addLocationsEntity(LocationsEntity newLocationsEntity, @Context UriInfo uriInfo) {

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
    public Response updateLocationsEntity(@PathParam("id") Short id, LocationsEntity updatedLocationsEntity) {
        if (!id.equals(updatedLocationsEntity.getLocationId())) {
            throw new BadRequestException();
        }

        String errorMessage = BeanValidator.validateBean(LocationsEntity.class, updatedLocationsEntity);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        LocationsEntity existingLocationsEntity = _locationsEntityRepository
                .findOptional(id)
                .orElseThrow(NotFoundException::new);
        // TODO: copy properties from the updated entity to the existing entity such as copy the version property shown below
        existingLocationsEntity.setCity(updatedLocationsEntity.getCity());
        existingLocationsEntity.setPostalCode(updatedLocationsEntity.getPostalCode());
        existingLocationsEntity.setStateProvince(updatedLocationsEntity.getStateProvince());
        existingLocationsEntity.setStreetAddress(updatedLocationsEntity.getStreetAddress());

        try {
            _locationsEntityRepository.update(existingLocationsEntity);
        } catch (OptimisticLockException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The data you are trying to update has changed since your last read request.")
                    .build();
        } catch (Exception ex) {
            // Return an HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "200 OK" and include in the body of the response the object that was updated
        return Response.ok(existingLocationsEntity).build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response delete(@PathParam("id") Short id) {

        LocationsEntity existingLocationsEntity = _locationsEntityRepository
                .findOptional(id)
                .orElseThrow(NotFoundException::new);

        try {
            _locationsEntityRepository.remove(existingLocationsEntity);    // Removes the LocationsEntity from being persisted
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

