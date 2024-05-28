package org.huzz.freeshare.quark.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenji
 * @since 1.0.0
 */
@Getter
@Setter
public class Resp<T> {
    private Integer status;
    private Integer code;
    private String message;
    private Long timestamp;
    T data;
}
