package org.huzz.freeshare.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenji
 * @since 1.0.0
 */
public class ExtRestTemplate extends RestTemplate {

    protected HttpHeaders defaultHeaders;
    protected static final Map<String, ?> EMPTY = Map.of();

    protected ObjectMapper objectMapper = new ObjectMapper();

    public ExtRestTemplate() {
        this(null);
    }

    public ExtRestTemplate(HttpHeaders defaultHeaders) {
        List<HttpMessageConverter<?>> messageConverters = getMessageConverters();
        for (HttpMessageConverter<?> converter : messageConverters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jsonConverter) {
                jsonConverter.getObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
                List<MediaType> newMediaType = new ArrayList<>(jsonConverter.getSupportedMediaTypes());
                newMediaType.add(MediaType.TEXT_HTML);
                jsonConverter.setSupportedMediaTypes(newMediaType);
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

    public ExtRestTemplate addDefaultHeader(HttpHeaders httpHeaders) {
        if (defaultHeaders == null) {
            defaultHeaders = new HttpHeaders();
        }
        defaultHeaders.addAll(httpHeaders);
        return this;
    }

    // 将对象转换为 MultiValueMap 的方法
    public MultiValueMap<String, Object> convertObjectToMap(Object obj) {
        HashMap<String, Object> fieldMap = objectMapper.convertValue(obj, new TypeReference<>() {});
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(fieldMap);
        return multiValueMap;
    }
}
