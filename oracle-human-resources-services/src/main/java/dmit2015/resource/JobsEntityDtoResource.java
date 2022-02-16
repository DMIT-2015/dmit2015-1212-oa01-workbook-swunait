package dmit2015.resource;

import common.validator.BeanValidator;
import dmit2015.entity.JobsEntity;
import dmit2015.dto.JobsEntityDto;
import dmit2015.mapper.JobsEntityMapper;
import dmit2015.repository.JobsEntityRepository;
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
@Path("JobsEntityDtos")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class JobsEntityDtoResource {

    @Inject
    private JobsEntityRepository _jobsEntityRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response listJobsEntitys() {
        return Response.ok(
                _jobsEntityRepository
                        .list()
                        .stream()
                        .map(JobsEntityMapper.INSTANCE::toDto)
                        .collect(Collectors.toList())
        ).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findJobsEntityById(@PathParam("id") Long jobsEntityId) {
        JobsEntity existingJobsEntity = _jobsEntityRepository.findOptional(jobsEntityId).orElseThrow(NotFoundException::new);

        JobsEntityDto dto = JobsEntityMapper.INSTANCE.toDto(existingJobsEntity);

        return Response.ok(dto).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response addJobsEntity(JobsEntityDto dto, @Context UriInfo uriInfo) {
        JobsEntity newJobsEntity = JobsEntityMapper.INSTANCE.toEntity(dto);
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
    public Response updateJobsEntity(@PathParam("id") Long jobsEntityId, JobsEntityDto dto) {
        if (!jobsEntityId.equals(dto.getJobId())) {
            throw new BadRequestException();
        }

        _jobsEntityRepository.findOptional(jobsEntityId).orElseThrow(NotFoundException::new);
        JobsEntity existingJobsEntity = JobsEntityMapper.INSTANCE.toEntity(dto);

        String errorMessage = BeanValidator.validateBean(JobsEntity.class, existingJobsEntity);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            _jobsEntityRepository.update(existingJobsEntity);
        } catch (Exception ex) {
            // Return an HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the JobsEntity was successfully persisted
        return Response.noContent().build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response delete(@PathParam("id") Long jobsEntityId) {

        _jobsEntityRepository.findOptional(jobsEntityId).orElseThrow(NotFoundException::new);

        try {
            _jobsEntityRepository.delete(jobsEntityId);    // Removes the JobsEntity from being persisted
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

