package com.easy.pay.spring.boot.pay.demo.controller;

import com.alibaba.fastjson.JSON;
import com.easy.pay.spring.boot.pay.demo.service.WebSocketService;
import com.niezhiliang.simple.pay.dto.AlipayPcPayDTO;
import com.niezhiliang.simple.pay.dto.AlipayQrcodeDTO;
import com.niezhiliang.simple.pay.dto.AlipayRefundDTO;
import com.niezhiliang.simple.pay.dto.AlipayRefundQueryDTO;
import com.niezhiliang.simple.pay.utils.PayUtils;
import com.niezhiliang.simple.pay.vos.AlipayCallBackVO;
import com.niezhiliang.simple.pay.vos.AlipayQrcodeVO;
import com.niezhiliang.simple.pay.vos.AlipayRefundQueryVO;
import com.niezhiliang.simple.pay.vos.AlipayRefundVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/9 上午10:57
 * 支付宝支付Controller
 */
@RestController
@RequestMapping(value = "alipay")
public class AliPayController {

    /**
     * 支付成功返回的状态值
     */
    private static final String SUCCESS_PAY_STATUS = "TRADE_SUCCESS";

    /**
     * 生成二维码
     * @param qrcodeDTO
     * @return
     */
    @RequestMapping(value = "qrcode")
    public AlipayQrcodeVO qrcode(AlipayQrcodeDTO qrcodeDTO) {

        return PayUtils.alipayQrcode(qrcodeDTO);
    }

    /**
     * 支付宝pc端支付
     */
    @RequestMapping(value = "pcpay")
    public String pcPay(AlipayPcPayDTO pcPayDTO){
        return PayUtils.alpayPcPay(pcPayDTO);
    }

    /**
     * 支付回调
     * @param request
     */
    @RequestMapping(value = "callback")
    public String payCallBack(HttpServletRequest request){
        AlipayCallBackVO aliPayCallBackVO = PayUtils.alipayPayCallBack(request);

        //支付成功通过websocket将回调结果返回给前端，我们生产环境需要判断是否回调结果状态并改变数据库中订单的值
        if(aliPayCallBackVO.getTrade_status().equals(SUCCESS_PAY_STATUS)) {
            WebSocketService.sendMessage(JSON.toJSONString(aliPayCallBackVO),aliPayCallBackVO.getOut_trade_no());

        }
        //返回给支付宝回调的接口已经封装好了，不管成功时候都是返回SUCCESS
        return aliPayCallBackVO.getShouldResonse();
    }

    /**
     * 支付宝退款
     */
    @RequestMapping(value = "refund")
    public AlipayRefundVO refund(AlipayRefundDTO refundDTO) {
        return PayUtils.alipayRefund(refundDTO);
    }

    /**
     * 支付宝退款查询
     * @param refundQueryDTO
     * @return
     */
    @RequestMapping(value = "refundQuery")
    public AlipayRefundQueryVO refundQuery(AlipayRefundQueryDTO refundQueryDTO) {
        return PayUtils.alipayRefundQuery(refundQueryDTO);
    }
}
