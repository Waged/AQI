package in.purelogic.aqi.live;


import com.squareup.otto.Bus;

import in.purelogic.aqi.infrastucture.AqiApplication;

public class BaseLiveActivity {
    protected AqiApplication application ;
    protected Bus bus ;
    //constructor


    public BaseLiveActivity(AqiApplication application) {
        this.application = application;
        bus=application.getBus();
        bus.register(this);
    }
}
