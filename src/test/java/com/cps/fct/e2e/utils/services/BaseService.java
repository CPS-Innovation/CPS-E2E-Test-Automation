package com.cps.fct.e2e.utils.services;

import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.httpClient.DefaultHttpService;
import org.picocontainer.annotations.Inject;

import java.util.Map;
import java.util.Objects;

public class BaseService {

    @Inject
    protected ScenarioContext context;

    @Inject
    protected DefaultHttpService service;

    protected Map<String, String> ddeiHeaders() {
        return Map.of(
                "x-functions-key", EnvConfig.get("X_FUNCTIONS_KEY"),
                "Accept", "application/json",
                "Content-Type", "application/json",
                "Cms-Auth-Values", Objects.requireNonNull(context.getAsString("Cms-Auth-Values"))
        );
    }
}
