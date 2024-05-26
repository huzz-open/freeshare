package org.huzz.rest;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author chenji
 * @since 1.0.0
 */
public class RestTemplateExt extends RestTemplate {

    private final HttpHeaders defaultHeaders;
    private static final Map<String, ?> EMPTY = Map.of();

    public RestTemplateExt() {
        this(null);
    }

    public RestTemplateExt(HttpHeaders defaultHeaders) {
        List<HttpMessageConverter<?>> messageConverters = getMessageConverters();
        for (HttpMessageConverter<?> converter : messageConverters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jsonConverter) {
                jsonConverter.getObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
                break;
            }
        }
        this.defaultHeaders = defaultHeaders;
    }


    public <T> T getForObject(String url, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return exchange(url, HttpMethod.GET, null, responseType, uriVariables).getBody();
    }

    public <T> T getForObject(String url, ParameterizedTypeReference<T> responseType) throws RestClientException {
        return getForObject(url, responseType, EMPTY);
    }

    @Override
    protected <T> T doExecute(URI url, String uriTemplate, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        RequestCallback wrappedRequestCallback = requestCallback;
        if (defaultHeaders != null) {
            wrappedRequestCallback = request -> {
                request.getHeaders().putAll(defaultHeaders);
                requestCallback.doWithRequest(request);
            };
        }
        return super.doExecute(url, uriTemplate, method, wrappedRequestCallback, responseExtractor);
    }
}
