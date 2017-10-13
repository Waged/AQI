package in.purelogic.aqi.infrastucture;

import android.app.Application;

import com.squareup.otto.Bus;

import in.purelogic.aqi.live.Module;


public class AqiApplication extends Application {

    private Bus bus ;

    public AqiApplication() {
        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Module.Register(this);
    }

    public Bus getBus() {
        return bus;
    }
}
