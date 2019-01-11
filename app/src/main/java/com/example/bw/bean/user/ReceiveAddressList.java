package com.example.bw.bean.user;

import java.util.List;

public class ReceiveAddressList {

    /**
     * result : [{"address":"河北省 唐山市 玉田县 ","createTime":1546970408000,"id":398,"phone":"18911474114","realName":"唐唐唐","userId":754,"whetherDefault":1,"zipCode":"164100"},{"address":"河北省 唐山市 玉田县 ","createTime":1546970425000,"id":399,"phone":"18911474114","realName":"唐唐唐","userId":754,"whetherDefault":2,"zipCode":"164100"}]
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
         * address : 河北省 唐山市 玉田县
         * createTime : 1546970408000
         * id : 398
         * phone : 18911474114
         * realName : 唐唐唐
         * userId : 754
         * whetherDefault : 1
         * zipCode : 164100
         */

        private String address;
        private long createTime;
        private int id;
        private String phone;
        private String realName;
        private int userId;
        private int whetherDefault;
        private String zipCode;
private boolean isEnter;

        public boolean isEnter() {
            if(whetherDefault==1){
                return true;
            }else{
                return false;
            }
        }

        public void setEnter(boolean enter) {
            isEnter = enter;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getWhetherDefault() {
            return whetherDefault;
        }

        public void setWhetherDefault(int whetherDefault) {
            this.whetherDefault = whetherDefault;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }
}
