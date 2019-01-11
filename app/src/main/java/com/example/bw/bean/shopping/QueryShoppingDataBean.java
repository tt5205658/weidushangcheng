package com.example.bw.bean.shopping;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class QueryShoppingDataBean implements Parcelable {

    /**
     * result : [{"commodityId":23,"commodityName":"小白鞋 女款 时尚百搭休闲板鞋","count":2,"pic":"http://172.17.8.100/images/small/commodity/nx/bx/6/1.jpg","price":139}]
     * message : 查询成功
     * status : 0000
     */
    private String message;
    private String status;
    private List<ResultBean> result;

    protected QueryShoppingDataBean(Parcel in) {
        message = in.readString();
        status = in.readString();
    }

    public static final Creator<QueryShoppingDataBean> CREATOR = new Creator<QueryShoppingDataBean>() {
        @Override
        public QueryShoppingDataBean createFromParcel(Parcel in) {
            return new QueryShoppingDataBean(in);
        }

        @Override
        public QueryShoppingDataBean[] newArray(int size) {
            return new QueryShoppingDataBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(status);
    }

    public static class ResultBean implements Parcelable{
        /**
         * commodityId : 23
         * commodityName : 小白鞋 女款 时尚百搭休闲板鞋
         * count : 2
         * pic : http://172.17.8.100/images/small/commodity/nx/bx/6/1.jpg
         * price : 139
         */

        private int commodityId;
        private String commodityName;
        private int count;
        private String pic;
        private double price;
        private boolean checked=false;

        protected ResultBean(Parcel in) {
            commodityId = in.readInt();
            commodityName = in.readString();
            count = in.readInt();
            pic = in.readString();
            price = in.readDouble();
            checked = in.readByte() != 0;
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel in) {
                return new ResultBean(in);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(commodityId);
            dest.writeString(commodityName);
            dest.writeInt(count);
            dest.writeString(pic);
            dest.writeDouble(price);
            dest.writeByte((byte) (checked ? 1 : 0));
        }
    }
}
