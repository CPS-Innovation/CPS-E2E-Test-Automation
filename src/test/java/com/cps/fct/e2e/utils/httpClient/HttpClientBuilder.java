package com.cps.fct.e2e.utils.httpClient;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpClientBuilder {

    RestAssuredConfig restConfig = RestAssuredConfig.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                    .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000)// timeout request max of 30S
                    .setParam(CoreConnectionPNames.SO_TIMEOUT, 90000));

    private static final Logger logger = LoggerFactory.getLogger(HttpClientBuilder.class);
    private final String baseUri;
    private final String endpoint;
    @Getter
    private final String method;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;
    private final Map<String, String> formParams;
    private final String body;
    private final int retryCount;
    @Getter
    private final String resourceName;
    RequestSpecification requestSpecification =  RestAssured
            .given()
            .config(restConfig)
            .relaxedHTTPSValidation();
    private int retryDelayMillis = 10_000;

    private HttpClientBuilder(Builder builder) {
        this.baseUri = builder.baseUri;
        this.endpoint = builder.endpoint;
        this.method = builder.method;
        this.headers = Map.copyOf(builder.headers);
        this.queryParams = Map.copyOf(builder.queryParams);
        this.formParams = builder.formParams!=null ? Map.copyOf(builder.formParams):Map.of();
        this.body = builder.body;
        this.retryCount = builder.retryCount;
        this.resourceName = builder.resourceName;
        this.retryDelayMillis = builder.retryDelayMillis;

    }

    public Response execute() {
        RequestSpecification request = requestSpecification
                .baseUri(baseUri)
                .basePath(endpoint)
                .headers(headers);


        if (formParams!=null && !formParams.isEmpty()) {
            request.formParams(formParams);
        }

        if (queryParams!=null) {
            request.queryParams(queryParams);
        }
        if (body!=null) {
            request.body(body);
        }


        Response response = null;
        int attempts = 0;

        while (attempts <= retryCount) {
            try {
                response = switch (method.toUpperCase()) {
                    case "GET" -> request.get();
                    case "POST" -> request.post();
                    case "PUT" -> request.put();
                    case "DELETE" -> request.delete();
                    case "PATCH" -> request.patch();
                    default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
                };
                break;
            } catch (Exception e) {
                logger.warn("Attempt {} failed: {}", attempts + 1, e.getMessage());
                attempts++;
                if (attempts > retryCount) throw e;

                try {
                    Thread.sleep(retryDelayMillis); // 10 seconds delay only on retry
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", ie);
                }

            }
        }

        return response;
    }


    public static class Builder {
        private final Map<String, String> headers = new HashMap<>();
        private final Map<String, String> queryParams = new HashMap<>();
        public Map<String, String> formParams = new HashMap<>();
        ;
        private String baseUri;
        private String endpoint;
        private String method = "GET";
        private String body;
        private int retryCount = 0;
        private String resourceName;
        private int retryDelayMillis; // default to 10 seconds

        public Builder baseUri(String baseUri) {
            this.baseUri = baseUri;
            return this;
        }

        public Builder endpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder addHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder addQueryParam(String key, String value) {
            this.queryParams.put(key, value);
            return this;
        }


        public Builder addFormParams(Map<String, String> params) {
            if (params!=null) {
                this.formParams.putAll(params);
            }
            return this;
        }

        public Builder addHeaders(Map<String, String> headers) {
            if (headers!=null) {
                this.headers.putAll(headers);
            }
            return this;
        }


        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder retry(int count) {
            this.retryCount = count;
            return this;
        }

        public Builder resourceName(String name) {
            this.resourceName = name;
            return this;
        }


        public Builder retryDelay(int millis) {
            this.retryDelayMillis = millis;
            return this;
        }


        public HttpClientBuilder build() {
            return new HttpClientBuilder(this);
        }
    }
}


