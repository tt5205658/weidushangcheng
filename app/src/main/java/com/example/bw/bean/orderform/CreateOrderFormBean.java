package com.example.bw.bean.orderform;

public class CreateOrderFormBean {
    private int commodityId;
    private int amount;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CreateOrderFormBean(int commodityId, int amount) {

        this.commodityId = commodityId;
        this.amount = amount;
    }
}
