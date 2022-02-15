package dmit2015.resource;


import common.validator.BeanValidator;
import dmit2015.entity.JobsEntity;
import dmit2015.repository.JobsEntityRepository;
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
@Path("JobsEntitys")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class JobsEntityResource {

    @Inject
    private JobsEntityRepository _jobsEntityRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response listJobsEntitys() {
        return Response.ok(_jobsEntityRepository.list()).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findJobsEntityById(@PathParam("id") Long jobsEntityId) {
        JobsEntity existingJobsEntity = _jobsEntityRepository.findOptional(jobsEntityId).orElseThrow(NotFoundException::new);

        return Response.ok(existingJobsEntity).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response addJobsEntity(JobsEntity newJobsEntity, @Context UriInfo uriInfo) {

        String errorMessage = BeanValidator.validateBean(JobsEntity.class, newJobsEntity);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            // Persist the new JobsEntity into the database
            _jobsEntityRepository.create(newJobsEntity);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // userInfo is injected via @Context parameter to this method
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(newJobsEntity.getJobId())
                .build();

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the JobsEntity was successfully persisted
        return Response
                .created(location)
                .build();
    }

    @PUT            // This method only accepts HTTP PUT requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response updateJobsEntity(@PathParam("id") String id, JobsEntity updatedJobsEntity) {
        if (!id.equals(updatedJobsEntity.getJobId())) {
            throw new BadRequestException();
        }

        String errorMessage = BeanValidator.validateBean(JobsEntity.class, updatedJobsEntity);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        JobsEntity existingJobsEntity = _jobsEntityRepository
                .findOptional(id)
                .orElseThrow(NotFoundException::new);
        // TODO: copy properties from the updated entity to the existing entity such as copy the version property shown below
        existingJobsEntity.setJobTitle(updatedJobsEntity.getJobTitle());
        existingJobsEntity.setMaxSalary(updatedJobsEntity.getMaxSalary());
        existingJobsEntity.setMinSalary(updatedJobsEntity.getMinSalary());

        try {
            _jobsEntityRepository.update(existingJobsEntity);
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
        return Response.ok(existingJobsEntity).build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response delete(@PathParam("id") String id) {

        JobsEntity existingJobsEntity = _jobsEntityRepository
                .findOptional(id)
                .orElseThrow(NotFoundException::new);

        try {
            _jobsEntityRepository.remove(existingJobsEntity);    // Removes the JobsEntity from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the JobsEntity was successfully deleted
        return Response.noContent().build();
    }

}

