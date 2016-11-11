package com.luoye.demo.weather;

/**
 * Created by Luoye on 2016/11/7.
 */

public class WeatherInfo {

    /**
     * errNum : 0
     * errMsg : success
     * retData : {"city":"北京","pinyin":"beijing","citycode":"101010100","date":"16-10-14","time":"11:00","postCode":"100000","longitude":116.391,"latitude":39.904,"altitude":"33","weather":"霾","temp":"23","l_tmp":"13","h_tmp":"23","WD":"无持续风向","WS":"微风(<10km/h)","sunrise":"06:24","sunset":"17:36"}
     */

    private int errNum;
    private String errMsg;
    /**
     * city : 北京
     * pinyin : beijing
     * citycode : 101010100
     * date : 16-10-14
     * time : 11:00
     * postCode : 100000
     * longitude : 116.391
     * latitude : 39.904
     * altitude : 33
     * weather : 霾
     * temp : 23
     * l_tmp : 13
     * h_tmp : 23
     * WD : 无持续风向
     * WS : 微风(<10km/h)
     * sunrise : 06:24
     * sunset : 17:36
     */

    private RetDataBean retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public RetDataBean getRetData() {
        return retData;
    }

    public void setRetData(RetDataBean retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        @Override
        public String toString() {
            return "RetDataBean{" +
                    "city='" + city + '\'' +
                    ", pinyin='" + pinyin + '\'' +
                    ", citycode='" + citycode + '\'' +
                    ", date='" + date + '\'' +
                    ", time='" + time + '\'' +
                    ", postCode='" + postCode + '\'' +
                    ", longitude=" + longitude +
                    ", latitude=" + latitude +
                    ", altitude='" + altitude + '\'' +
                    ", weather='" + weather + '\'' +
                    ", temp='" + temp + '\'' +
                    ", l_tmp='" + l_tmp + '\'' +
                    ", h_tmp='" + h_tmp + '\'' +
                    ", WD='" + WD + '\'' +
                    ", WS='" + WS + '\'' +
                    ", sunrise='" + sunrise + '\'' +
                    ", sunset='" + sunset + '\'' +
                    '}';
        }

        private String city;
        private String pinyin;
        private String citycode;
        private String date;
        private String time;
        private String postCode;
        private double longitude;
        private double latitude;
        private String altitude;
        private String weather;
        private String temp;
        private String l_tmp;
        private String h_tmp;
        private String WD;
        private String WS;
        private String sunrise;
        private String sunset;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getAltitude() {
            return altitude;
        }

        public void setAltitude(String altitude) {
            this.altitude = altitude;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getL_tmp() {
            return l_tmp;
        }

        public void setL_tmp(String l_tmp) {
            this.l_tmp = l_tmp;
        }

        public String getH_tmp() {
            return h_tmp;
        }

        public void setH_tmp(String h_tmp) {
            this.h_tmp = h_tmp;
        }

        public String getWD() {
            return WD;
        }

        public void setWD(String WD) {
            this.WD = WD;
        }

        public String getWS() {
            return WS;
        }

        public void setWS(String WS) {
            this.WS = WS;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }
    }

}
