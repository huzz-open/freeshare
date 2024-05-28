package org.huzz.freeshare.rest;

import org.springframework.util.MultiValueMap;

/**
 * @author chenji
 * @since 1.0.0
 */
public interface FormDataTransformer {
    MultiValueMap<String, Object> transform();
}
