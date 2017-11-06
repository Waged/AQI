package in.purelogic.aqi;



public class SensorNode {
    private double latitude ;
    private double longitude;
    private String aqiValue;
    private String pm25Value;
    private String humidity;
    private String temprature;
    private String locationName;
    private String condition;
    private String timeStamp;



    public SensorNode(double latitude, double longitude, String aqiValue, String pm25Value,
                      String humidity, String temprature, String locationName , String condition,String timeStamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.aqiValue = aqiValue;
        this.pm25Value = pm25Value;
        this.humidity = humidity;
        this.temprature = temprature;
        this.locationName = locationName;
        this.condition = condition;
        this.timeStamp = timeStamp;
    }

    //TODO:Setters
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAqiValue(String aqiValue) {
        this.aqiValue = aqiValue;
    }

    public void setPm25Value(String pm25Value) {
        this.pm25Value = pm25Value;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


    //TODO: Getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAqiValue() {
        return aqiValue;
    }

    public String getPm25Value() {
        return pm25Value;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTemprature() {
        return temprature;
    }

    public String getLocationName() {return locationName;}

    public String getCondition() {return condition;}

    public String getTimeStamp() {
        return timeStamp;
    }

}
