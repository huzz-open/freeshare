package org.huzz.freeshare.quark.rest;

import org.huzz.freeshare.quark.dto.ListFileDescDTO;
import org.huzz.freeshare.quark.dto.Resp;
import org.huzz.freeshare.rest.ExtCookieAuthRestTemplate;
import org.springframework.core.ParameterizedTypeReference;

/**
 * @author chenji
 * @since 1.0.0
 */
public class QuarkClient extends ExtCookieAuthRestTemplate {
    static final String BASE_URL = "https://drive-pc.quark.cn/1/clouddrive";

    public QuarkClient(String cookie) {
        super(cookie);
    }

    public Resp<ListFileDescDTO> listFileDesc(String pdirFid, int page, int size) {
        final String url =
                String.format("%s/file/sort?pr=ucpro&fr=pc&uc_param_str=&pdir_fid=%s&_page=%d&_size=%d&_fetch_total=1&_fetch_sub_dirs=0&_sort=file_type:asc,updated_at:desc", BASE_URL, pdirFid, page, size);
        return getForObject(url, new ParameterizedTypeReference<>(){});
    }

}
