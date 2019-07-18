package com.comarch.danielkurosz;


import com.comarch.danielkurosz.dao.MongoDatabaseConfigurator;
import com.comarch.danielkurosz.dao.MongoTagsDAO;
import com.comarch.danielkurosz.exceptions.AppExceptionMapper;
import com.comarch.danielkurosz.resources.TagsResource;
import com.comarch.danielkurosz.service.TagMapper;
import com.comarch.danielkurosz.service.TagsService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.setup.Environment;

public class TagsServiceApp extends Application<TagsServiceConfiguration> {

    private static TagsService tagsService;

    public static void main(String[] args) throws Exception {
        TagMapper tagMapper = new TagMapper();
        MongoTagsDAO mongoTagsDAO = MongoDatabaseConfigurator.configureMongo();
        tagsService = new TagsService(mongoTagsDAO, tagMapper);
        new TagsServiceApp().run(args);
    }

    @Override
    public void run(TagsServiceConfiguration configuration, Environment environment) {
        final TagsResource tagsResource = new TagsResource(tagsService);
        environment.jersey().register(tagsResource);
        environment.jersey().register(new AppExceptionMapper());

       environment.jersey().register(AuthFactory.binder(
               new BasicAuthFactory<>(
                       new TagServiceAuthenticator(configuration.getLogin(),configuration.getPassword()),
                               "SECURITY REALM",Boolean.class)));

        environment.jersey().register(new AppExceptionMapper());
        //environment.healthChecks().register("template", new RestCheck(configuration.getVersion()));
    }
}
