package com.example.bw.bean.home;

import java.util.List;

public class HomeSwitchCommodityBean {

    /**
     * result : [{"commodityId":161,"commodityName":"新款牛皮系带圆头车缝线耐磨舒适商务休闲男鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/swxxx/6/1.jpg","price":44,"saleNum":0},{"commodityId":158,"commodityName":"系带商务鞋休闲鞋皮鞋棉鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/swxxx/3/1.jpg","price":99,"saleNum":0},{"commodityId":160,"commodityName":"简约百搭商务休闲鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/swxxx/5/1.jpg","price":459,"saleNum":0},{"commodityId":157,"commodityName":"舒适百搭套脚商务休闲鞋男士皮鞋男鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/swxxx/2/1.jpg","price":249,"saleNum":0},{"commodityId":162,"commodityName":"冬季新款 牛皮纯色保暖绒里纯色套脚休闲鞋英伦风商务休闲鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/swxxx/7/1.jpg","price":258,"saleNum":0}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * commodityId : 161
         * commodityName : 新款牛皮系带圆头车缝线耐磨舒适商务休闲男鞋
         * masterPic : http://172.17.8.100/images/small/commodity/nx/swxxx/6/1.jpg
         * price : 44
         * saleNum : 0
         */

        private int commodityId;
        private String commodityName;
        private String masterPic;
        private int price;
        private int saleNum;

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getMasterPic() {
            return masterPic;
        }

        public void setMasterPic(String masterPic) {
            this.masterPic = masterPic;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(int saleNum) {
            this.saleNum = saleNum;
        }
    }
}
