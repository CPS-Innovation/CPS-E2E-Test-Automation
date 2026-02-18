package com.cps.fct.e2e.utils.httpClient;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HttpResponseWrapper {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseWrapper.class);

    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;

    private HttpResponseWrapper(Response response) {
        this.statusCode = response.getStatusCode();
        this.body = response.getBody().asPrettyString();
        this.headers = response.getHeaders().asList().stream()
                .collect(java.util.stream.Collectors.toMap(Header::getName, Header::getValue));
    }

    public static HttpResponseWrapper from(Response response) {
        return new HttpResponseWrapper(response);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}

