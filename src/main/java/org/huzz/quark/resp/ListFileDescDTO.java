package org.huzz.quark.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author chenji
 * @since 1.0.0
 */
@Getter
@Setter
public class ListFileDescDTO {
    private List<FileDescDTO> list;
}
