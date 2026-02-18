package com.cps.fct.e2e.utils.services.ddei;

import com.cps.fct.e2e.utils.common.EnvConfig;
import com.cps.fct.e2e.utils.common.ScenarioContext;
import com.cps.fct.e2e.utils.common.SecurePassCode;
import com.cps.fct.e2e.utils.httpClient.HttpClientBuilder;
import com.cps.fct.e2e.utils.httpClient.HttpResponseWrapper;
import com.cps.fct.e2e.utils.services.BaseService;
import org.apache.http.HttpStatus;

import java.util.Map;


public class CommonService extends BaseService {

    public void createCmsAuthToken(ScenarioContext context) {
        HttpResponseWrapper responseWrapper = service.sendRequest(authRequestParams(context));
        context.set("Cms-Auth-Values", responseWrapper.getBody());
    }

    public boolean isDDEIHealthy() {
        HttpResponseWrapper responseWrapper = service.sendRequest(healthCheck());
        return responseWrapper.getStatusCode()==HttpStatus.SC_OK;
    }

    private HttpClientBuilder authRequestParams(ScenarioContext context) {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint("/api/authenticate")
                .addHeaders(authHeaders())
                .addFormParams(formData(context))
                .method("POST")
                .retry(0)
                .resourceName("userService")
                .build();
    }

    private HttpClientBuilder healthCheck() {
        return new HttpClientBuilder.Builder()
                .baseUri(EnvConfig.get("DDEI_HOST"))
                .endpoint("/api/health-check")
                .addHeader("accept", "*/*")
                .method("GET")
                .retry(1)
                .resourceName("healthCheck")
                .build();
    }


    private Map<String, String> authHeaders() {
        return Map.of(
                "x-functions-key", EnvConfig.get("X_FUNCTIONS_KEY"),
                "Accept", "application/json",
                "Content-Type", "application/x-www-form-urlencoded"
        );
    }

    private Map<String, String> formData(ScenarioContext context) {

        String username = EnvConfig.getEnv("CPS_USER") + context.getAsString("envSuffix");
        String password = SecurePassCode.decode(EnvConfig.getEnv("PASSWORD"));
        return Map.of(
                "username", username,
                "password", password
        );
    }
}
