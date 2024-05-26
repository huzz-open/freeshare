package org.huzz.quark.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenji
 * @since 1.0.0
 */
@Getter
@Setter
public class FileDescDTO {
    private String fid;
    private String fileName;
    private Long size;
}
