### 支付功能集成

支付

- 创建支付配置表merchant_details
- 编写支付接口 PayMerchantController

```sql
DROP TABLE IF EXISTS `merchant_details`;

CREATE TABLE `merchant_details` (
  `details_id` char(32) CHARACTER SET utf8 NOT NULL COMMENT '列表id',
  `pay_type` varchar(16) CHARACTER SET utf8 NOT NULL COMMENT '支付类型(支付渠道) 详情查看com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform对应子类，aliPay 支付宝， wxPay微信..等等',
  `appid` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '应用id',
  `mch_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户id，商户号，合作伙伴id等等',
  `cert_store_type` varchar(16) CHARACTER SET utf8 DEFAULT NULL COMMENT '当前面私钥公钥为证书类型的时候，这里必填，可选值:PATH,STR,INPUT_STREAM',
  `key_private` mediumtext CHARACTER SET utf8 COMMENT '私钥或私钥证书 商户API秘钥',
  `key_public` mediumtext CHARACTER SET utf8 COMMENT '公钥或公钥证书',
  `key_cert` mediumtext COMMENT 'key证书,附加证书使用，如SSL证书，或者银联根级证书方面',
  `key_cert_pwd` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '私钥证书或key证书的密码',
  `notify_url` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '异步回调',
  `return_url` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '同步回调地址，大部分用于付款成功后页面转跳',
  `sign_type` varchar(16) CHARACTER SET utf8 NOT NULL COMMENT '签名方式,目前已实现多种签名方式详情查看com.egzosn.pay.common.util.sign.encrypt。MD5,RSA等等',
  `seller` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '收款账号，暂时只有支付宝部分使用，可根据开发者自行使用',
  `sub_app_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '子appid',
  `sub_mch_id` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '子商户id',
  `input_charset` varchar(16) CHARACTER SET utf8 NOT NULL COMMENT '编码类型，大部分为utf-8',
  `is_test` tinyint(1) NOT NULL COMMENT '是否为测试环境: 0 否，1 测试环境',
  `merchant_cert` mediumtext COMMENT '应用公钥证书',
  PRIMARY KEY (`details_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

```

```java

@Api(tags = "支付接口")
@Controller
@RequestMapping(value = "/pay",
        produces = "application/json; charset=UTF-8")
public class PayMerchantController {

    @Autowired
    private PayMerchantService payMerchantService;

    @ApiOperation(value = "微信APP支付")
    @ResponseBody
    @PostMapping("/wx/toAppPay")
    public CommonResult< Map<String, Object>> toPayWxApp(@RequestBody PayVo payVo) {
        PayBeforeVo payBeforeVo = payMerchantService.toPayWxApp(payVo);
        if(!payBeforeVo.isSuccess()){
            return CommonResult.error(1000,payBeforeVo.getMessage());
        }
        Map<String, Object> map = payBeforeVo.getMap();
        return CommonResult.success(map);
    }

    @ApiOperation(value = "支付宝APP支付")
    @ResponseBody
    @PostMapping("/ali/toAppPay")
    public CommonResult<String> toPayAliApp(@RequestBody PayVo payVo) {

        PayBeforeVo payBeforeVo = payMerchantService.toPayAliApp(payVo);
        if(!payBeforeVo.isSuccess()){
            return CommonResult.error(1001,payBeforeVo.getMessage());
        }
        Map<String, Object> map = payBeforeVo.getMap();

        String orderInfo =  UriVariables.getMapToParameters(map);

        return CommonResult.success(orderInfo);
    }

    @ApiOperation(value = "微信小程序支付")
    @ResponseBody
    @PostMapping("/wx/toWeiAppPay")
    public CommonResult< Map<String, Object>> toPayWeiApp(@RequestBody PayVo payVo) {
        PayBeforeVo payBeforeVo = payMerchantService.toPayWeiApp(payVo);
        if(!payBeforeVo.isSuccess()){
            return CommonResult.error(1002,payBeforeVo.getMessage());
        }
        Map<String, Object> map = payBeforeVo.getMap();
        return CommonResult.success(map);
    }

    @ApiOperation(value = "申请退款")
    @ResponseBody
    @PostMapping("/refund")
    public CommonResult<RefundResult> toRefund(@RequestBody PayRefundVo payRefundVo) {
        RefundResult refundResult = payMerchantService.toRefund(payRefundVo);
        if(!"10000".equals(refundResult.getCode())){
            return CommonResult.error(1003,refundResult.getMsg());
        }

        return CommonResult.success(refundResult);
    }

    @ApiOperation(value = "申请退款查询")
    @ResponseBody
    @PostMapping("/toRefundQuery")
    public CommonResult<Map<String, Object>> toRefundQuery(@RequestBody PayRefundQueryVo payRefundVo) {
        Map<String, Object> queryMap = payMerchantService.toRefundQuery(payRefundVo);
        if(!"".equals(queryMap.get("123"))){
            return CommonResult.error(1004,(String) queryMap.get("123"));
        }

        return CommonResult.success(queryMap);
    }

    @ApiOperation(value = "申请转账")
    @ResponseBody
    @PostMapping("/transfer")
    public CommonResult<Map<String, Object>> toTransfer(@RequestBody PayTransferVo payTransferVo) {
        Map<String, Object> map = payMerchantService.toTransfer(payTransferVo);
        if(!"10000".equals(map.get("123"))){
            return CommonResult.error(1004,(String) map.get("123"));
        }

        return CommonResult.success(map);
    }

    @ApiOperation(value = "申请转账查询")
    @ResponseBody
    @PostMapping("/transferQuery")
    public CommonResult<Map<String, Object>> toTransferQuery(@RequestBody PayTransferQueryVo payTransferVo) {
        Map<String, Object> map = payMerchantService.toTransferQuery(payTransferVo);
        if(!"".equals(map.get("123"))){
            return CommonResult.error(1004,(String) map.get("123"));
        }

        return CommonResult.success(map);
    }

}
```
