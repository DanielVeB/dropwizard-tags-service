package com.comarch.danielkurosz;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;


public class TagServiceAuthenticator implements Authenticator<BasicCredentials, Boolean> {

    private String login;
    private String password;

   public TagServiceAuthenticator(String login, String password){
       this.login = login;
       this.password = password;
   }

    @Override
    public Optional<Boolean> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if(credentials.getUsername().equals(this.login) && credentials.getPassword().equals(this.password)){
            return Optional.of(true);
        }return Optional.absent();
    }
}
