package in.purelogic.aqi.services;


import com.google.android.gms.maps.model.LatLng;

public class ForecastServices {

    public ForecastServices() {
    }

    public static class SearchForecastsRequest{
        public String query ;

        public SearchForecastsRequest(String query) {
            this.query = query;
        }
    }

    public static class SearchForecastsResponse{
        public String modifiedQuery ;

    }
    
}
