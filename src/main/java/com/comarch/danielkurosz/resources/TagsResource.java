package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.dto.UserTagDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.InvalidClientIdException;
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
    @Path("/userid={id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTags(@Auth Boolean isAuthenticated,
                            @PathParam("id") String id) throws AppException {
        LOGGER.info("get tags from user with id " + id);
        try {
            List<UserTagDTO> tags = tagsService.getTags(id);
            return Response.ok(tags).build();
        } catch (InvalidClientIdException ex) {
            return Response.ok(null).build();
        }
    }


    @POST
    @Timed
    @Path("/userid={id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Auth Boolean isAuthenticated,
                           @PathParam("id") String id) {
        LOGGER.info("create User with id " + id);
        try {
            tagsService.create(id);
            return Response.ok().build();
        } catch (AppException e) {
            return Response.ok(null).build();
        }
    }
}
