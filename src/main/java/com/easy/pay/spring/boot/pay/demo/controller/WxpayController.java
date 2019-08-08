package com.easy.pay.spring.boot.pay.demo.controller;

import com.alibaba.fastjson.JSON;
import com.easy.pay.spring.boot.pay.demo.service.WebSocketService;
import com.niezhiliang.simple.pay.dto.WxpayCloseOrderDTO;
import com.niezhiliang.simple.pay.dto.WxpayQrcodeDTO;
import com.niezhiliang.simple.pay.dto.WxpayRefundDTO;
import com.niezhiliang.simple.pay.dto.WxpayRefundQueryDTO;
import com.niezhiliang.simple.pay.utils.PayUtils;
import com.niezhiliang.simple.pay.vos.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/10 上午8:25
 */
@RestController
@RequestMapping(value = "wx")
@Slf4j
public class WxpayController {


    /**
     * 微信二维码支付
     * @param qrcodeDTO
     * @return
     */
    @RequestMapping(value = "qrcode")
    public WxpayQrcodeVO wxQrcode(WxpayQrcodeDTO qrcodeDTO) {

        return PayUtils.wxpayQrcode(qrcodeDTO);
    }

    /**
     * 微信关闭订单
     * @return
     */
    @RequestMapping(value = "closeOrder")
    public WxpayCloseOrderVO closeOrder(WxpayCloseOrderDTO closeOrderDTO) {
        return PayUtils.wxpayCloseOrder(closeOrderDTO);
    }

    /**
     * 微信支付回调
     * @param request
     * @return
     */
    @RequestMapping(value = "callback")
    public String payCallBack(HttpServletRequest request) {

        WxpayCallBackVO wxpayCallBackVO = PayUtils.wxpayNotify(request);
        log.info(wxpayCallBackVO.getOutTradeNo() +"-----"+ wxpayCallBackVO.getResultCode());
        //判断验签是否通过并且支付结果是不是成功
        if (wxpayCallBackVO.getSignResult() && wxpayCallBackVO.getResultCode().equals("SUCCESS")) {
            WebSocketService.sendMessage(JSON.toJSONString(wxpayCallBackVO),wxpayCallBackVO.getOutTradeNo());
            return "SUCCESS";
        }
        return "FAILER";
    }

    /**
     * 微信退款
     * @param wxpayRefundDTO
     * @return
     */
    @RequestMapping(value = "refund")
    public WxpayRefundVO wxRefund(WxpayRefundDTO wxpayRefundDTO) {
        return PayUtils.wxRefund(wxpayRefundDTO);
    }

    /**
     * 微信退款查询
     * @param refundQueryDTO
     * @return
     */
    @RequestMapping(value = "refundQuery")
    public WxpayRefundQueryVO refundQuery(WxpayRefundQueryDTO refundQueryDTO) {
        return PayUtils.wxRefundQuery(refundQueryDTO);
    }
}
