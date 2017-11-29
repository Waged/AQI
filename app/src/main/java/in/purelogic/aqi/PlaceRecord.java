package in.purelogic.aqi;

import com.google.android.gms.maps.model.LatLng;

public class PlaceRecord {

    String locationName;
    String aqi;
    int locationAqiImg;
    String locationMsg;
    String temp;
    String humid;
    String pm25;
    //LatLng latLng ;

    public PlaceRecord(String locationName,String aqi, String locationMsg, String temp, String humid, String pm25) {
        this.locationName = locationName;
        this.aqi = aqi;
        this.locationMsg = locationMsg;
        this.temp = temp;
        this.humid = humid;
        this.pm25 = pm25;

    }




//getters
    public String getLocationName() {
        return locationName;
    }

    public String getAqi() {
        return aqi;
    }

    public int getLocationAqiImg() {
        return locationAqiImg;
    }

    public String getLocationMsg() {
        return locationMsg;
    }

  //  public LatLng getLatLng() {

//        return latLng;
 //   }

    public String getTemp() {
        return temp;
    }

    public String getHumid() {
        return humid;
    }

    public String getPm25() {
        return pm25;
    }


}
