package com.yincheng.game.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qijianguo
 * 订单查询返回参数
 */
@NoArgsConstructor
@Data
public class SpQueryOrderResp{
    /**
     * result : ok
     * _id : 607e535e56363933e13b7410
     * merchantOrderId : sp20210420120653137284
     * parnterId : 607cf716debcb5ed5defa777
     * userid : ace
     * merchantid : 607cf716debcb5ed5defa777
     * merchantName : ace
     * mer_userid : 8
     * providerOrderId : 607e535f16a2fc403725b28d
     * payment : {"creditCards":{"mdr":0.03,"fix_fee":5000},"eWallets":{"mdr":0.03,"fix_fee":0},"va":{"fix_fee":7500,"mdr":0.01},"retailOutlets":{"mdr":0.01,"fix_fee":8500},"disbursement":{"mdr":0,"fix_fee":7500}}
     * money : 2
     * currency : IDR
     * type : UNIONPAYH5
     * time : 2021-04-20T04:06:54.353Z
     * lasttime : 2021-04-20T04:06:55.675Z
     * lasterr :
     * cb_url : http://
     * return_url : http://
     * status : 待支付
     * xendit_ret : {"id":"607e535f16a2fc403725b28d","external_id":"607e535e56363933e13b7410","user_id":"5f907faa11fb2540763d7a2a","status":"PENDING","merchant_name":"Sinobay","merchant_profile_picture_url":"https://xnd-companies.s3.amazonaws.com/prod/1617504849850_255.jpg","amount":2,"payer_email":"userrefused@to.provide","description":"Default goods","expiry_date":"2021-04-21T04:06:55.399Z","invoice_url":"https://checkout.xendit.co/web/607e535f16a2fc403725b28d","available_banks":[],"available_ewallets":[{"ewallet_type":"DANA"},{"ewallet_type":"LINKAJA"},{"ewallet_type":"OVO"}],"should_exclude_credit_card":true,"should_send_email":false,"success_redirect_url":"http://","created":"2021-04-20T04:06:55.567Z","updated":"2021-04-20T04:06:55.567Z","currency":"IDR"}
     * received : -1
     * outOrderId : sp20210420120653137284
     */
    /** 错误，错误可能是字符串，对象，数字等，一般会有可以阅读部分。如果有err，以下参数全部不存在 */
    private String err;
    /** 你方系统的订单 */
    private String outOrderId;
    /** Sinopay的订单id */
    private String orderId;
    /** 提供方的订单id,请注意有的供应商不会给出这个参数 */
    private String providerOrderId;
    /**  订单要求支付的金额 */
    private String money;
    /** 用户支付的金额 */
    private String received;
    /** 币种 */
    private String currency;
//    /** 订单状态 */
//    private String status;

    private String result;
    private String _id;
    private String merchantOrderId;
    private String parnterId;
    private String userid;
    private String merchantid;
    private String merchantName;
    private String mer_userid;
    private PaymentBean payment;
    private String type;
    private String time;
    private String lasttime;
    private String lasterr;
    private String cb_url;
    private String return_url;
    private XenditRetBean xendit_ret;

    @NoArgsConstructor
    @Data
    public static class PaymentBean {
        /**
         * creditCards : {"mdr":0.03,"fix_fee":5000}
         * eWallets : {"mdr":0.03,"fix_fee":0}
         * va : {"fix_fee":7500,"mdr":0.01}
         * retailOutlets : {"mdr":0.01,"fix_fee":8500}
         * disbursement : {"mdr":0,"fix_fee":7500}
         */

        private CreditCardsBean creditCards;
        private EWalletsBean eWallets;
        private VaBean va;
        private RetailOutletsBean retailOutlets;
        private DisbursementBean disbursement;

        @NoArgsConstructor
        @Data
        public static class CreditCardsBean {
            /**
             * mdr : 0.03
             * fix_fee : 5000
             */

            private double mdr;
            private int fix_fee;
        }

        @NoArgsConstructor
        @Data
        public static class EWalletsBean {
            /**
             * mdr : 0.03
             * fix_fee : 0
             */

            private double mdr;
            private int fix_fee;
        }

        @NoArgsConstructor
        @Data
        public static class VaBean {
            /**
             * fix_fee : 7500
             * mdr : 0.01
             */

            private int fix_fee;
            private double mdr;
        }

        @NoArgsConstructor
        @Data
        public static class RetailOutletsBean {
            /**
             * mdr : 0.01
             * fix_fee : 8500
             */

            private double mdr;
            private int fix_fee;
        }

        @NoArgsConstructor
        @Data
        public static class DisbursementBean {
            /**
             * mdr : 0
             * fix_fee : 7500
             */

            private int mdr;
            private int fix_fee;
        }
    }

    @NoArgsConstructor
    @Data
    public static class XenditRetBean {
        /**
         * id : 607e535f16a2fc403725b28d
         * external_id : 607e535e56363933e13b7410
         * user_id : 5f907faa11fb2540763d7a2a
         * status : PENDING
         * merchant_name : Sinobay
         * merchant_profile_picture_url : https://xnd-companies.s3.amazonaws.com/prod/1617504849850_255.jpg
         * amount : 2
         * payer_email : userrefused@to.provide
         * description : Default goods
         * expiry_date : 2021-04-21T04:06:55.399Z
         * invoice_url : https://checkout.xendit.co/web/607e535f16a2fc403725b28d
         * available_banks : []
         * available_ewallets : [{"ewallet_type":"DANA"},{"ewallet_type":"LINKAJA"},{"ewallet_type":"OVO"}]
         * should_exclude_credit_card : true
         * should_send_email : false
         * success_redirect_url : http://
         * created : 2021-04-20T04:06:55.567Z
         * updated : 2021-04-20T04:06:55.567Z
         * currency : IDR
         */

        private String id;
        private String external_id;
        private String user_id;
        private String status;
        private String merchant_name;
        private String merchant_profile_picture_url;
        private int amount;
        private String payer_email;
        private String description;
        private String expiry_date;
        private String invoice_url;
        private boolean should_exclude_credit_card;
        private boolean should_send_email;
        private String success_redirect_url;
        private String created;
        private String updated;
        private String currency;
        private List<?> available_banks;
        private List<AvailableEwalletsBean> available_ewallets;

        @NoArgsConstructor
        @Data
        public static class AvailableEwalletsBean {
            /**
             * ewallet_type : DANA
             */

            private String ewallet_type;
        }
    }



}
