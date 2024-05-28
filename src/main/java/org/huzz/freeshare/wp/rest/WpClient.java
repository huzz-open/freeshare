package org.huzz.freeshare.wp.rest;

import org.huzz.freeshare.rest.ExtCookieAuthRestTemplate;
import org.huzz.freeshare.rest.FormDataTransformer;
import org.huzz.freeshare.wp.dto.CreatePostsResp;
import org.huzz.freeshare.wp.dto.Posts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;

/**
 * @author chenji
 * @since 1.0.0
 */
public class WpClient extends ExtCookieAuthRestTemplate {

    static final String BASE_URL = "https://gaoqian365.com";
    static final String ADMIN_AJAX_URL = BASE_URL + "/wp-admin/admin-ajax.php";

    public WpClient(String cookie) {
        super(cookie);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        getMessageConverters().add(new FormHttpMessageConverter());
        this.addDefaultHeader(httpHeaders);
    }

    public CreatePostsResp createPosts(Posts posts) {
        return postForObject(posts, CreatePostsResp.class);
    }

    private <T> T postForObject(FormDataTransformer transformer, Class<T> respClass) {
        return postForObject(ADMIN_AJAX_URL, transformer.transform(), respClass);
    }
}
