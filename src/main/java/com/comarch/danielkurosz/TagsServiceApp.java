package com.comarch.danielkurosz;


import com.comarch.danielkurosz.resources.TagsResource;
import com.comarch.danielkurosz.service.TagsService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class TagsServiceApp extends Application<TagsServiceConfiguration> {

    private static TagsService tagsService;

    public static void main(String[] args) throws Exception {



        new TagsServiceApp().run(args);
    }

    @Override
    public void run(TagsServiceConfiguration configuration, Environment environment) {
        final TagsResource tagsResource = new TagsResource(tagsService);
        environment.jersey().register(tagsResource);
        //environment.jersey().register(new AppExceptionMapper());
        //environment.healthChecks().register("template", new RestCheck(configuration.getVersion()));
    }
}
