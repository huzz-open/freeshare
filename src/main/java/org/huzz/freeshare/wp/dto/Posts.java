package org.huzz.freeshare.wp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huzz.freeshare.rest.FormDataTransformer;
import org.huzz.freeshare.wp.constant.ParamFields;
import org.huzz.freeshare.wp.constant.PostsAction;
import org.huzz.freeshare.wp.constant.ZibpayS;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * @author chenji
 * @since 1.0.0
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Posts implements FormDataTransformer {
    private Long id;
    private String title;
    private String content;
    private PostsAction action;

    private List<Integer> category;
    private Zibpay zibpay;
    private ZibpayS zibpayS;
    private List<String> tags;

    @Override
    public MultiValueMap<String, Object> transform() {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.add(ParamFields.POSTS_ID, id);
        multiValueMap.add(ParamFields.POST_TITLE, title);
        multiValueMap.add(ParamFields.POST_CONTENT, content);
        multiValueMap.add(ParamFields.ACTION, action.name());

        if (!CollectionUtils.isEmpty(category)) {
            category.forEach(c -> multiValueMap.add(ParamFields.CATEGORY, c));
        }

        if (zibpay != null) {
            multiValueMap.add(ParamFields.POSTS_ZIBPAY_MODO, zibpay.getPayModo().name());
            multiValueMap.add(ParamFields.POSTS_ZIBPAY_PRICE, zibpay.getPayPrice());
            multiValueMap.add(ParamFields.POSTS_ZIBPAY_POINTS, zibpay.getPointsPrice());
        }

        multiValueMap.add(ParamFields.ZIBPAY_S, zibpayS);

        multiValueMap.add(ParamFields.TAGS, tags);

        return multiValueMap;
    }
}
