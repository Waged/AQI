package in.purelogic.aqi.live;

import in.purelogic.aqi.infrastucture.AqiApplication;


public class Module {

    public static void Register(AqiApplication application){

        new LiveForecastsService(application);

    }

}
