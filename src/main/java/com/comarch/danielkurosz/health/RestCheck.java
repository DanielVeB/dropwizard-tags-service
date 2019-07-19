package com.comarch.danielkurosz.health;

import com.codahale.metrics.health.HealthCheck;

public class RestCheck extends HealthCheck {
    private final String version;

    public RestCheck(String version) {
        this.version = version;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy("OK with version: " + this.version);
    }
}
