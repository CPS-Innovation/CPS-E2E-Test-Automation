package com.cps.fct.e2e.utils.httpClient;

import io.restassured.response.Response;
import org.picocontainer.annotations.Inject;

public class DefaultHttpService {

    @Inject
    private HttpStatusValidator statusValidator;

    public HttpResponseWrapper sendRequest(HttpClientBuilder client) {
        Response response = client.execute();
        statusValidator.validateOrThrow(response, client.getMethod());
        HttpResponseWrapper wrapped = HttpResponseWrapper.from(response);
        ResourceResponseStore.add(client.getResourceName(), wrapped);
        return wrapped;
    }

    public HttpResponseWrapper getResponsesByResource(String resourceName) {
        return ResourceResponseStore.getLatestResponse(resourceName);
    }

}


