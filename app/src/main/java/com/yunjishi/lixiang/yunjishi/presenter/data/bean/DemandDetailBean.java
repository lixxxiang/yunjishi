package com.yunjishi.lixiang.yunjishi.presenter.data.bean;

public class DemandDetailBean {

    /**
     * status : 200
     * message : success
     * data : {"demandType":1,"resolution":"1m-3m","startTime":1529078400000,"endTime":1532275200000,"times":5,"demandArea":1.18569944653965E13,"geo":"{\"type\":\"MultiPolygon\",\"coordinates\":[[[[108.28125,31.952162],[108.28125,54.162434],[163.125,54.162434],[163.125,31.952162],[108.28125,31.952162]]],[[[101.953125,48.922499],[101.953125,58.077876],[120.9375,58.077876],[120.9375,48.922499],[101.953125,48.922499]]]]}","demandStatus":3}
     */

    private int status;
    private String message;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * demandType : 1
         * resolution : 1m-3m
         * startTime : 1529078400000
         * endTime : 1532275200000
         * times : 5
         * demandArea : 1.18569944653965E13
         * geo : {"type":"MultiPolygon","coordinates":[[[[108.28125,31.952162],[108.28125,54.162434],[163.125,54.162434],[163.125,31.952162],[108.28125,31.952162]]],[[[101.953125,48.922499],[101.953125,58.077876],[120.9375,58.077876],[120.9375,48.922499],[101.953125,48.922499]]]]}
         * demandStatus : 3
         */

        private int demandType;
        private String resolution;
        private long startTime;
        private long endTime;
        private int times;
        private double demandArea;
        private String geo;
        private int demandStatus;

        public int getDemandType() {
            return demandType;
        }

        public void setDemandType(int demandType) {
            this.demandType = demandType;
        }

        public String getResolution() {
            return resolution;
        }

        public void setResolution(String resolution) {
            this.resolution = resolution;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public double getDemandArea() {
            return demandArea;
        }

        public void setDemandArea(double demandArea) {
            this.demandArea = demandArea;
        }

        public String getGeo() {
            return geo;
        }

        public void setGeo(String geo) {
            this.geo = geo;
        }

        public int getDemandStatus() {
            return demandStatus;
        }

        public void setDemandStatus(int demandStatus) {
            this.demandStatus = demandStatus;
        }
    }
}
