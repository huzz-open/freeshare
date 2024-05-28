package org.huzz.freeshare.rest;

import org.springframework.http.HttpHeaders;

/**
 * @author chenji
 * @since 1.0.0
 */
public class ExtCookieAuthRestTemplate extends ExtRestTemplate {
    public ExtCookieAuthRestTemplate(String cookie) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.COOKIE, cookie);
        this.defaultHeaders = httpHeaders;
    }
}
