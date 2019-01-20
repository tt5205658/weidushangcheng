package com.example.bw.bean.my;

import java.util.List;

public class MyWalletBean {

    /**
     * result : {"balance":99994982,"detailList":[{"amount":542,"consumerTime":1547554403000,"orderId":"20190115195843894754","userId":754},{"amount":700,"consumerTime":1547552817000,"orderId":"20190112081848359754","userId":754},{"amount":347,"consumerTime":1547552734000,"orderId":"20190112083602398754","userId":754},{"amount":189,"consumerTime":1547550007000,"orderId":"20190115103545362754","userId":754},{"amount":422,"consumerTime":1547549697000,"orderId":"20190115150545227754","userId":754}]}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * balance : 99994982
         * detailList : [{"amount":542,"consumerTime":1547554403000,"orderId":"20190115195843894754","userId":754},{"amount":700,"consumerTime":1547552817000,"orderId":"20190112081848359754","userId":754},{"amount":347,"consumerTime":1547552734000,"orderId":"20190112083602398754","userId":754},{"amount":189,"consumerTime":1547550007000,"orderId":"20190115103545362754","userId":754},{"amount":422,"consumerTime":1547549697000,"orderId":"20190115150545227754","userId":754}]
         */

        private double balance;
        private List<DetailListBean> detailList;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public static class DetailListBean {
            /**
             * amount : 542
             * consumerTime : 1547554403000
             * orderId : 20190115195843894754
             * userId : 754
             */

            private int amount;
            private long consumerTime;
            private String orderId;
            private int userId;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public long getConsumerTime() {
                return consumerTime;
            }

            public void setConsumerTime(long consumerTime) {
                this.consumerTime = consumerTime;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
