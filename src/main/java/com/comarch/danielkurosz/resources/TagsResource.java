package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.auth.UserAuth;
import com.comarch.danielkurosz.dto.ClientTagDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.service.TagsService;
import io.dropwizard.auth.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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
    @Path("/clients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTags(@Auth UserAuth userAuth,
                            @QueryParam("client_id") List<String> clientsId) {

        LOGGER.info("get tags");
        List<ClientTagDTO> clientTagDTOs= tagsService.getTags(clientsId);
        return Response.ok(clientTagDTOs).build();
    }

    @GET
    @Timed
    @Path("/clients/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientIdsByTags(@Auth UserAuth userAuth,
                                       @QueryParam("tag_id!")List<String> notExistTagsId,
                                       @QueryParam("tag_id") List<String> tagsId){

        LOGGER.info("Get clients id without tags: " + notExistTagsId +" or/and with tags "+ tagsId);

        List<String> clientsId = tagsService.getClientsId(notExistTagsId,tagsId);
        return Response.ok(clientsId).build();
    }


    @POST
    @Timed
    @Path("/client={client_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Auth UserAuth userAuth,
                           @PathParam("client_id") String clientID) {

        LOGGER.info("create User with id " + clientID);

        try {
            ClientTagDTO client =tagsService.create(clientID);
            return Response.ok(client).build();
        } catch (AppException e) {
            return Response.ok(null).build();
        }
    }

    @PUT
    @Path("/client")
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void addZodiac(@Auth UserAuth userAuth, ClientTagDTO clientTagDTO){

        LOGGER.info("update tags for client with id: "+ clientTagDTO.getClientId());
        tagsService.update(clientTagDTO);

    }

}
