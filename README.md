### spring-boot-easy-pay-demo

> 这是一个Easy-Pay与springboot框架来实现支付宝和微信支付的使用Demo

#### 完善配置

> 该项目我会跑在我自己的服务器上，如果您是在本地跑该项目，支付宝的回调地址和回调页面无需修改，微信回调也无需修改，直接用我的就好
如果支付成功，我服务器会通过websocket发送信息给你本地，您本地就能收到支付成功的信息，总之，本地环境跑的话别的，只需要修改配置文件的
appid 公私钥等 别的千万别改。安全问题您可以放心，支付宝和微信回调都不会暴露您的公私钥之类的。可以大胆的填我线上的回调地址。

```yaml
#支付宝支付参数配置
alipay:
  #应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
  appId: ''
  #商户私钥，您的PKCS8格式RSA2私钥
  privateKey:
  publicKey:
  #(无需修改)服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
  notifyUrl: 'http://www.niezhiliang.com:9999/alipay/callback'
  #(无需修改)页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
  returnUrl: 'http://www.niezhiliang.com:9999/success'
  #签名方式(无需修改)
  signType: RSA2
  #字符编码格式(无需修改)
  charset: utf-8
  #支付宝网关(无需修改)
  gatewayUrl: 'https://openapi.alipay.com/gateway.do'
  #保存支付日志的地址(无需修改)
  logPath: /tmp/
wxpay:
  #公众号appid
  appId:
  #商户id
  mchId: ''
  #支付api安全密钥
  mchKey: ''
  #支付类型
  tradeType: 'NATIVE'
  #支付结果回调地址
  payNotify: 'http://www.niezhiliang.com:9999/wx/callback'
  #退款结果回调
  refundNotify:
  #根目录下的证书名称
  certName: 'wx_pay_cert.p12'
```
#### 启动项目

`Application.java`该类有个`Main`方法，直接启动`Main`方法就将项目启动起来了

#### 访问项目

```html
127.0.0.1:9999/index
```

  


