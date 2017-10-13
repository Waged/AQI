package in.purelogic.aqi.live;

import com.squareup.otto.Subscribe;

import in.purelogic.aqi.infrastucture.AqiApplication;
import in.purelogic.aqi.services.ForecastServices;



public class LiveForecastsService extends BaseLiveActivity{


    public LiveForecastsService(AqiApplication application) {
        super(application);
    }

    @Subscribe
    public void getForecastMessage(ForecastServices.SearchForecastsRequest request){
        ForecastServices.SearchForecastsResponse response = new ForecastServices.SearchForecastsResponse();
        response.modifiedQuery=request.query+"This query was modified Haha";
        bus.post(response);
    }




}
