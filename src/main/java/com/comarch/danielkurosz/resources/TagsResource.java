package com.comarch.danielkurosz.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.danielkurosz.data.TagEntity;
import com.comarch.danielkurosz.data.UserTagsEntity;
import com.comarch.danielkurosz.dto.UserTagDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.service.TagsService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
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
    public Response getTags(@PathParam("id") String id) throws AppException {

        List<UserTagDTO> tags = tagsService.getTags(id);

        return Response.ok(tags).build();
    }
}
