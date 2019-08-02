package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.auth.UserAuth;
import com.comarch.danielkurosz.data.Tag;
import com.comarch.danielkurosz.dto.ClientTagDTO;
import com.comarch.danielkurosz.dto.ClientTagsDTO;
import com.comarch.danielkurosz.dto.Statistics;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.service.TagsService;
import io.dropwizard.auth.Auth;

import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Path("/tags")
public class TagsResource {

    private TagsService tagsService;

    private static final Logger LOGGER = Logger.getLogger(TagsService.class.getName());

    public TagsResource(TagsService tagsService) {
        this.tagsService = tagsService;
    }


    @GET
    @Timed
    @Path("/client={client_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTagsByClientId(@Auth UserAuth userAuth,
                                      @PathParam("client_id") String clientId,
                                      @QueryParam("tag_id") String tagId,
                                      @QueryParam("tag_value") String tagValue,
                                      @QueryParam("limit") @Min(0) @DefaultValue("10") int limit,
                                      @QueryParam("offset") @Min(0) @DefaultValue("0") int offset) {

        LOGGER.info("Get tags for client with id: "+ clientId);

        List<Tag> tags = tagsService.getTagsByClientId(clientId, new Tag(tagId, tagValue), limit, offset);
        return Response.ok(tags).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTagsWithClientId(@Auth UserAuth userAuth,
                                       @QueryParam("tag_id") String tagId,
                                       @QueryParam("tag_value") String tagValue,
                                       @QueryParam("limit") @Min(0) @DefaultValue("10") int limit,
                                       @QueryParam("offset") @Min(0) @DefaultValue("0") int offset) {

        LOGGER.info("get tags with client id ");

        Tag tag = new Tag(tagId, tagValue);
        List<ClientTagDTO> tags = tagsService.getTagsWithClientId(tag, limit, offset);
        return Response.ok(tags).build();
    }

    @GET
    @Path("/clients_id")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientsId(@Auth UserAuth userAuth,
                                 @QueryParam("tag_id") String tagId,
                                 @QueryParam("tag_value") String tagValue,
                                 @QueryParam("limit") @Min(0) @DefaultValue("10") int limit,
                                 @QueryParam("offset") @Min(0) @DefaultValue("0") int offset) {

        LOGGER.info("get clients id ");

        Tag tag = new Tag(tagId, tagValue);
        List<UUID> tags = tagsService.getClientsUUID(tag, limit, offset);
        return Response.ok(tags).build();
    }

    @GET
    @Timed
    @Path("/stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatistics(@Auth UserAuth userAuth,
                                  @QueryParam("sort") @DefaultValue("0") int sort,
                                  @QueryParam("limit") @Min(0) @DefaultValue("10") int limit,
                                  @QueryParam("offset") @Min(0) @DefaultValue("0") int offset){
        LOGGER.info("Get statistics");

        List<Statistics> stats = tagsService.getStats(limit,offset,sort);
        return Response.ok(stats).build();
    }

    @POST
    @Timed
    @Path("/client")
    @Produces(MediaType.APPLICATION_JSON)
    public void create(@Auth UserAuth userAuth,
                       ClientTagsDTO clientTagDTO) throws AppException {

        LOGGER.info("create User with id " + clientTagDTO.getClientId());
        tagsService.create(clientTagDTO);

    }

    @PUT
    @Path("/client")
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void addZodiac(@Auth UserAuth userAuth, ClientTagsDTO clientTagDTO) {

        LOGGER.info("update tags for client with id: " + clientTagDTO.getClientId());
        tagsService.update(clientTagDTO);

    }

}
