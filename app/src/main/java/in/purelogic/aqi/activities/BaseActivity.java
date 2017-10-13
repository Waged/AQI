package in.purelogic.aqi.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;

import in.purelogic.aqi.infrastucture.AqiApplication;


public class BaseActivity extends AppCompatActivity {
protected AqiApplication application ;
    protected Bus bus ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (AqiApplication)getApplication();
        bus = application.getBus();
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
