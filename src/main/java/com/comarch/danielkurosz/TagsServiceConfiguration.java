package com.comarch.danielkurosz;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class TagsServiceConfiguration extends Configuration {

    @NotEmpty
    private String version;

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;


    @JsonProperty
    public String getVersion() {
        return version;
    }

    @JsonProperty
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty
    public String getLogin() {
        return login;
    }

    @JsonProperty
    public void setLogin(String login) {
        this.login = login;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
