package org.huzz.freeshare.wp.dto;

import lombok.Getter;
import lombok.Setter;
import org.huzz.freeshare.wp.constant.ZibpayModo;

/**
 * @author chenji
 * @since 1.0.0
 */
@Getter
@Setter
public class Zibpay {
    private ZibpayModo payModo;
    private Integer payPrice;
    private Integer pointsPrice;
}
