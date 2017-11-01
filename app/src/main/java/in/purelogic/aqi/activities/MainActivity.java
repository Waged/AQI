package in.purelogic.aqi.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.wang.avi.AVLoadingIndicatorView;
import com.yalantis.phoenix.PullToRefreshView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.purelogic.aqi.R;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,LocationListener{
    // Base URL
    final String url = "https://www.facebook.com/aqiindia/";
    String myPlaceNow;
    String myPlaceNowSmall;
    @BindView(R.id.drawer_layout)
    FlowingDrawer mDrawer;
    @BindView(R.id.btnLocations)
    ImageButton btnLocation;
    @BindView(R.id.btnNotification)
    ImageButton btnNotify;
    @BindView(R.id.btnWhatAqi)
    ImageButton btnWhatAqi;
    @BindView(R.id.btnBlog)
    ImageButton btnBlog;
    @BindView(R.id.btnWebsite)
    ImageButton btnWebsite;
    @BindView(R.id.btnAboutUs)
    ImageButton btnAboutUs;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvPlace)
    TextView tvPlace;
    @BindView(R.id.tvClock)
    TextView tvClock;
    @BindView(R.id.tvAqi)
    TextView tvAqi;
    @BindView(R.id.tvAqiComment)
    TextView tvAqiComment;
    @BindView(R.id.tvLastRefresh)
    TextView tvLastRefresh;
    @BindView(R.id.pull_to_refresh)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.btnFacebook)
    ImageButton btnFacebook;
    @BindView(R.id.tvCurrentLocation)
    TextView tvCurrentLocation;
    @BindView(R.id.myCardView)
    CardView locationCard;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.chart)
    BarChart chart;
    Animation fade;
    MediaPlayer mp;
    //SharedPreferences locationSharedPreferences;
   // public static final String myLocationPrefs = "locationPrefs" ;
   // public static final String latPrefs = "latitude";
    //public static final String lngPrefs = "longitude";
   // public static final String aqiPrefs = "aqiValue";


    // TODO: Declare a LocationManager and a LocationListener here:
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    double latitude, longitude;
    String NETWORK_LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
    final long MIN_TIME = 0;        // Time between location updates (5000 milliseconds or 5 seconds)
    final float MIN_DISTANCE = 0;  // Distance between location updates (1000m or 1km)
    public static boolean gps_enabled = false;
    public static boolean network_enabled = false;
    final int REQUEST_CODE = 1;

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart", "Called");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "Called");
        if (android.os.Build.VERSION.SDK_INT <= 22) {
            Log.e("sdkLess22", " right");
            LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            try {
                 gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                Log.d("gps","status: "+gps_enabled);
            } catch (Exception ex) {
                Log.e("gps", ex.toString());
            }
            try {
                 network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                 Log.d("network","status: "+gps_enabled);
            } catch (Exception ex) {
                Log.e("network", ex.toString());
            }
            if (!gps_enabled && !network_enabled) {
                displayPromptForEnablingGPS(this);
            }
        } else {
            Log.e("sdkGreater22", " right");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
                Log.e("permGrantedInOnresume", "right");
            } else {
                displayPromptForEnablingGPS(this);
                Log.e("permGrantedInOnresume", "right");
            }
        }
        getWeatherForCurrentLocation();
        Log.e("getWeatherForCurrentLoc","Called from onResume");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //TODO:Typeface for Texts
        //Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/moodyrock.ttf");
        //   Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/arizona.ttf");
        //   Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/grand_hotel.otf");
        Typeface tfRobotoBlack = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
        Typeface tfRobotoBlackItalic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BlackItalic.ttf");
        Typeface tfRobotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface tfRobotoBoldCondensed = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BoldCondensed.ttf");
        //   Typeface tfRobotoBoldItalic = Typeface.createFromAsset(getAssets(),"fonts/Roboto-BoldItalic.ttf");
        fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        mp = MediaPlayer.create(getApplicationContext(), R.raw.btnclick);
        tvPlace.setTypeface(tfRobotoBoldCondensed, Typeface.BOLD);
        tvDate.setTypeface(tfRobotoBlack, Typeface.BOLD);
        tvClock.setTypeface(tfRobotoBlack);
        tvAqi.setTypeface(tfRobotoBoldCondensed);
        tvAqiComment.setTypeface(tfRobotoBlackItalic);
        tvLastRefresh.setTypeface(tfRobotoBlackItalic);
        tvCurrentLocation.setTypeface(tfRobotoBold);
        tvCurrentLocation.setTextSize(18);
        tvCurrentLocation.setAnimation(fade);

        Date date = Calendar.getInstance().getTime();
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day = (String) DateFormat.format("dd", date); // 20
        String monthString = (String) DateFormat.format("MMM", date); // Jun

        tvDate.setText(dayOfTheWeek + ", " + monthString + " " + day);
        locationCard.setCardBackgroundColor(Color.TRANSPARENT);
        locationCard.setCardElevation(4.0f);
        //locationSharedPreferences = getSharedPreferences(myLocationPrefs, Context.MODE_PRIVATE);

        //Todo: Elastic Drawer to view Settings
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");
                    //   Toast.makeText(MainActivity.this, "onDrawerStateChange ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                // Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
                //  Toast.makeText(MainActivity.this, "onDrawerSlide ", Toast.LENGTH_SHORT).show();
            }
        });
        //Todo: Pull to refresh view
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "Refreshing..", Toast.LENGTH_SHORT).show();
                        getWeatherForCurrentLocation();
                        Date date = Calendar.getInstance().getTime();
                        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
                        String day = (String) DateFormat.format("dd", date); // 20
                        String monthString = (String) DateFormat.format("MMM", date); // Jun
                        String hourString = (String) DateFormat.format("HH", date); // Jun
                        String minuteString = (String) DateFormat.format("mm", date);
                        tvLastRefresh.setText(hourString + ":" + minuteString + " " + dayOfTheWeek + ", " + monthString + " " + day); // we are
                    }
                }, 900);
            }
        });



        BarData data = new BarData(getXAxisValues(),getDataSet());
        chart.setData(data);
        //chart.setDescription("AQI Level");
        chart.animateXY(2000, 2000);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.invalidate();
        chart.setDrawGridBackground(false);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isActivated()) {
            Toasty.info(MainActivity.this, "drawer is activated", Toast.LENGTH_SHORT, false).show();
            mDrawer.closeMenu(true);
        } else {
            super.onBackPressed();
            Toasty.error(MainActivity.this, "Closed", Toast.LENGTH_SHORT, false).show();
        }
    }


    //For Menu to Override method
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@Nullable MenuItem item) {
        return true;
    }



    // TODO: Add getWeatherForCurrentLocation() here:
    private void getWeatherForCurrentLocation() {
        avi.show();
        Log.e("getWeather", "Called");
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.e("onLocationChanged", "called");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.e("onLocationChanged", "Latitude =" + latitude + " longitude =" + longitude);
                if (latitude != 0.0 && longitude != 0.0) {
                    Log.e("location","location Achieved");
                    new FindMe(MainActivity.this).execute();
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("onProviderDisabled", "called");
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            return;
        }

        mLocationManager.requestLocationUpdates(NETWORK_LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }


    //TODO: Location Listeners
    @Override
    public void onLocationChanged(Location location) {
       //double lat =  location.getLatitude();
      // double lng = location.getLongitude();
        /*
        SharedPreferences.Editor editor = locationSharedPreferences.edit();
        editor.putString(latPrefs, lat+"");
        editor.putString(lngPrefs, lng+"");
        //editor.putString(aqiPrefs, e);
        editor.commit();
        */
        Log.e("onLocationChanged","Called");
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    //TODO: AsyncTask to fetch user location
    private class FindMe extends AsyncTask<Void, Void, String> {
        private Context appContext;
        private FindMe(Context appContext) {
            this.appContext = appContext;
        }
        @Override
        protected String doInBackground(Void... voids) {
            Log.e("doInBackground", "Called");
            Geocoder geocoder = new Geocoder(appContext, Locale.getDefault());
            List<Address> addresses ;

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && addresses.size() > 0) {
                    Log.e("Adress", addresses.toString());
                    Address address = addresses.get(0);
                   // String cityName = address.getAddressLine(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");

                    for (int i = 0; i <= 2; i++) {
                        strReturnedAddress.append(address.getAddressLine(i)).append(",");
                    }
                    String strAdd = strReturnedAddress.toString();
                   // String state = addresses.get(0).getAdminArea();
                    String city = addresses.get(0).getLocality();
                    String country = addresses.get(0).getCountryName();
                    String knownName = addresses.get(0).getFeatureName();
                  //  String full = address.getAddressLine(0);
                    Log.e("cityName", city);
                    // tvPlace.setText(cityName+" "+stateName);
                    if (knownName != null) {
                         myPlaceNow = knownName + ", " + city + ", " + country;
                         myPlaceNowSmall =knownName + ", " + city;
                        if(myPlaceNow.length() > 33){
                            return myPlaceNowSmall.trim();
                        }else{
                            return myPlaceNow.trim();
                        }


                    }
                    Log.e("knownName", "is empty");
                    return strAdd;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Couldn't locate..";
            }
            return null;
        }
        protected void onPostExecute(String result) {
            tvPlace.setText(result);
            tvCurrentLocation.setText(result);
            avi.hide();
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeatherForCurrentLocation();
                Log.e("getWeatherForCurrentLoc","Called from onRequest");
            } else {
                Log.d("clima", "OnRequestPermission:permission Failed");
                // Toasty.error(this, "App needs Permision to fetch AQI", Toast.LENGTH_SHORT).show();
                 //tvCurrentLocation.setText("Enable Permission");
                // tvPlace.setText("GPS Off");
            }
        }
    }

    public static void displayPromptForEnablingGPS(final Activity activity) {
        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Do you want open GPS setting?";
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            Log.e("gps", ex.toString());
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            Log.e("network", ex.toString());
        }
        if (!gps_enabled && !network_enabled) {


            builder.setMessage(message)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    activity.startActivity(new Intent(action));
                                    d.dismiss();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    d.cancel();
                                }
                            });
            builder.create().show();
        }
    }

    //ToDO: Managing Clicks
    @OnClick(R.id.btnLocations)
    void locationButton() {
        mp.start();
        //btnLocation.startAnimation(fade);
        Intent map = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(map);
       // mDrawer.closeMenu();
    }

    @OnClick(R.id.btnNotification)
    void notificationbtn() {
        Toast.makeText(this, "btnNotification", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnWhatAqi)
    void whatsAQIbtn() {
        Toast.makeText(this, "What's AQI ?", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnBlog)
    void blogBtn() {
       // btnBlog.setAnimation(fade);
        Toasty.info(MainActivity.this, "Blog Redirecting .. ", Toast.LENGTH_SHORT, false).show();
        Intent blog = new Intent(MainActivity.this, Blog.class);
        startActivity(blog);
        mp.start();
       // mDrawer.closeMenu();
    }

    @OnClick(R.id.btnWebsite)
    void ourWebBtn() {
        btnWebsite.setAnimation(fade);
        mp.start();
        Toasty.info(MainActivity.this, "Webpage Redirecting .. ", Toast.LENGTH_SHORT, false).show();
        mp.start();
        //btnWebsite.setAnimation(fade);
        String url = "http://aqi.in/";
        Intent web = new Intent(Intent.ACTION_VIEW);
       // mDrawer.closeMenu();
        web.setData(Uri.parse(url));
        startActivity(web);
        

    }

    @OnClick(R.id.btnAboutUs)
    void aboutUsBtn() {
        mp.start();
        //btnAboutUs.startAnimation(fade);
        Intent aboutUs = new Intent(MainActivity.this, AboutUs.class);
      //  mDrawer.closeMenu();
        startActivity(aboutUs);
    }

    @OnClick(R.id.btnFacebook)
    void facebookBtn() {
        mp.start();
        //btnFacebook.setAnimation(fade);
       // mDrawer.closeMenu();
        Toasty.info(MainActivity.this, "Facebook Redirecting", Toast.LENGTH_SHORT).show();
        startActivity(newFacebookIntent(getPackageManager(), url));
    }


    @OnClick(R.id.myCardView)
    void goToMaps () {
        Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
        if(latitude !=0 && longitude!=0){
        mapIntent.putExtra("latitude",latitude);
        mapIntent.putExtra("longitude",longitude);
        mapIntent.putExtra("aqi","22");
        mapIntent.putExtra("location",myPlaceNow);
        }
        startActivity(mapIntent);
    }


    //facebook re-directing
    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                Log.d("facebookredirecting", "welldone");
            }
        } catch (PackageManager.NameNotFoundException ignored) {
            Log.d("facebookredirecting", "badme");
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets ;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
        valueSet2.add(v2e6);
        ArrayList<BarEntry> valueSet3 = new ArrayList<>();
        BarEntry v3e1 = new BarEntry(110.000f, 0); // Jan
        valueSet3.add(v3e1);
        BarEntry v3e2 = new BarEntry(40.000f, 1); // Feb
        valueSet3.add(v3e2);
        BarEntry v3e3 = new BarEntry(60.000f, 2); // Mar
        valueSet3.add(v3e3);
        BarEntry v3e4 = new BarEntry(30.000f, 3); // Apr
        valueSet3.add(v3e4);
        BarEntry v3e5 = new BarEntry(90.000f, 4); // May
        valueSet3.add(v3e5);
        BarEntry v3e6 = new BarEntry(100.000f, 5); // Jun
        valueSet3.add(v3e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Good");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Bad");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet barDataSet3 = new BarDataSet(valueSet3, "very bad");
        barDataSet3.setColors(ColorTemplate.VORDIPLOM_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);
        return dataSets;
    }
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }




}

