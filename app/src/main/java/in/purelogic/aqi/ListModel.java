package in.purelogic.aqi;

/**
 * Created by Waged on 11/10/2017.
 */

public class ListModel {

    String locationName;
        String locationAqiImg;
        String locationMsg;


        public ListModel(String locationName, String locationAqiImg, String locationMsg) {
            this.locationName = locationName;
            this.locationAqiImg = locationAqiImg;
            this.locationMsg=locationMsg;

        }


    public String getLocationName() {
        return locationName;
    }

    public String getLocationAqiImg() {
        return locationAqiImg;
    }

    public String getLocationMsg() {
        return locationMsg;
    }


}
