package com.comarch.danielkurosz;

import com.comarch.danielkurosz.auth.UserAuth;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TagServiceAuthenticator implements Authenticator<BasicCredentials, UserAuth> {

    private final static Logger LOGGER = LoggerFactory.getLogger(TagServiceAuthenticator.class);

    private String login;
    private String password;

    TagServiceAuthenticator(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Optional<UserAuth> authenticate(BasicCredentials credentials) throws AuthenticationException {
        LOGGER.debug("Trying to authenticate user: "+ credentials.getUsername());
        if (credentials.getUsername().equals(this.login) && credentials.getPassword().equals(this.password)) {
            return Optional.of(new UserAuth(credentials.getUsername()));
        }
        return Optional.absent();
    }
}
