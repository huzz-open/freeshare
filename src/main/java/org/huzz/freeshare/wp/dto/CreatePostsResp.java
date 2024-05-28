package org.huzz.freeshare.wp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author chenji
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class CreatePostsResp {
    private Long postsId;
    private String url;
    private String msg;
    private Boolean reload;
    @JsonProperty("goto")
    private String gotoX;
    private Boolean error;
}
