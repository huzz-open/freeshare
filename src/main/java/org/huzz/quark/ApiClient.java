package org.huzz.quark;

import org.huzz.quark.resp.ListFileDescDTO;
import org.huzz.quark.resp.Resp;
import org.huzz.rest.RestTemplateExt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;

/**
 * @author chenji
 * @since 1.0.0
 */
public class ApiClient {
    static final String BASE_URI = "https://drive-pc.quark.cn/1/clouddrive";


    private final RestTemplateExt rt;

    public ApiClient(String cookie) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.COOKIE, cookie);
        this.rt = new RestTemplateExt(httpHeaders);
    }

    public Resp<ListFileDescDTO> listFileDesc(String pdirFid, int page, int size) {
        final String url =
                String.format("%s/file/sort?pr=ucpro&fr=pc&uc_param_str=&pdir_fid=%s&_page=%d&_size=%d&_fetch_total=1&_fetch_sub_dirs=0&_sort=file_type:asc,updated_at:desc", BASE_URI, pdirFid, page, size);
        return rt.getForObject(url, new ParameterizedTypeReference<>(){});
    }

}
