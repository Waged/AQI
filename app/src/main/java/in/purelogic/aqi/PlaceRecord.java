package in.purelogic.aqi;

import com.google.android.gms.maps.model.LatLng;

public class PlaceRecord {

    String locationName;
    int locationAqiImg;
    String locationMsg;
    String temp;
    String humid;
    String pm25;
    LatLng latLng ;

    public PlaceRecord(String locationName, String locationMsg, String temp, String humid, String pm25) {
        this.locationName = locationName;
        this.locationMsg = locationMsg;
        this.temp = temp;
        this.humid = humid;
        this.pm25 = pm25;

    }




//getters
    public String getLocationName() {
        return locationName;
    }

    public int getLocationAqiImg() {
        return locationAqiImg;
    }

    public String getLocationMsg() {
        return locationMsg;
    }

    public LatLng getLatLng() {

        return latLng;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumid() {
        return humid;
    }

    public String getPm25() {
        return pm25;
    }

//Setters
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLocationAqiImg(int locationAqiImg) {
        this.locationAqiImg = locationAqiImg;
    }

    public void setLocationMsg(String locationMsg) {
        this.locationMsg = locationMsg;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setHumid(String humid) {
        this.humid = humid;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

}
