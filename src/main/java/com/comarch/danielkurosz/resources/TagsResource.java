package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.auth.UserAuth;
import com.comarch.danielkurosz.dto.UserTagDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.service.TagsService;
import io.dropwizard.auth.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
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
        HashMap<String, List<UserTagDTO>> tags = tagsService.getTags(clientsId);
        return Response.ok(tags).build();
    }

    @POST
    @Timed
    @Path("/client={client_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Auth UserAuth userAuth,
                           @PathParam("client_id") String clientID) {
        LOGGER.info("create User with id " + clientID);
        try {
            List<UserTagDTO> tags = tagsService.create(clientID);
            return Response.ok(tags).build();
        } catch (AppException e) {
            return Response.ok(null).build();
        }
    }
}
