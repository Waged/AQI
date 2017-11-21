package in.purelogic.aqi.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.purelogic.aqi.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMapLongClickListener {


    @BindView(R.id.map_tvMyLocation)
    TextView tvLocation;

    @BindView(R.id.ivFav)
    ImageButton ivAddToFav;

    @BindView(R.id.map_tvTemp)
    TextView tvTemp;

    @BindView(R.id.map_tvHumid)
    TextView tvHumi;

    @BindView(R.id.map_tvPm25)
    TextView tvPm25;

    private GoogleMap mMap;
    String myLocation;
    String aqi;
    double latitude;
    double longitude;
    boolean isFavourite = false;
    int location = -1;
    LocationManager locationManager;
    String provider;
    Bundle bundle;
    BitmapDescriptor icon;
    Marker m = null;

    @Override
    protected void onPause() {
        super.onPause();
       // locationManager.removeUpdates((LocationListener) MapsActivity.this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
         icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_icon);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
         bundle = getIntent().getExtras();


        //tvAqi.setText(aqi);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = getIntent();
        location = i.getIntExtra("locationInfo", -1);
      //  setUpMapIfNeeded();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(MapsActivity.this);
        if (bundle != null) {
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
            myLocation = bundle.getString("location");
            aqi = bundle.getString("aqi");
            tvLocation.setText(myLocation);
            LatLng myPlace = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(myPlace).title(myLocation).icon(icon).snippet("Current Location! "));

            for(int rad=100;rad<=500;rad+=10) {
                CircleOptions circleOptions = new CircleOptions()
                        .center(new LatLng(latitude, longitude))   //set center
                        .radius(rad)   //set radius in meters
                        .fillColor(Color.TRANSPARENT)  //default
                        .strokeColor(Color.RED)
                        .strokeWidth(2);

                Circle myCircle = googleMap.addCircle(circleOptions);
            }

            /*
            mMap.addCircle(new CircleOptions()
                    .center(new LatLng(latitude, longitude))
                    .radius(2000)
                    .strokeColor(Color.GREEN)
                    .fillColor(Color.RED))
                    ;
*/
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
        else {
            //delhi in map
            LatLng delhiPlace = new LatLng(28.7041, 77.1025);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(delhiPlace));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(8));
            tvLocation.setText("NA");
            Toasty.error(this,"GPS or Connection problem !",Toast.LENGTH_SHORT).show();
           //Toast.makeText(this, "No place Provided check GPS", Toast.LENGTH_SHORT).show();
        }



    }


    @OnClick(R.id.ivFav)
    public void addToFavourites() {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        if (!isFavourite) {
            ivAddToFav.setImageResource(R.drawable.ic_favorites);
            isFavourite = true;
        } else {
            ivAddToFav.setImageResource(R.drawable.ic_notinfavorites);
            isFavourite = false;
        }

    }


    @Override
    public void onMapLongClick(LatLng point) {

        Toast.makeText(this, "Long Pressed", Toast.LENGTH_SHORT).show();
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String label = new Date().toString();
        //reference to the marker
        if (m != null) { //if marker exists (not null or whatever)
            try {
                List<Address> listAddresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                if (listAddresses != null && listAddresses.size() > 0) {
                    label = listAddresses.get(0).getAddressLine(0);
                    m.setPosition(point);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {


            try {
                List<Address> listAddresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                if (listAddresses != null && listAddresses.size() > 0) {
                    label = listAddresses.get(0).getAddressLine(0);
                    m = mMap.addMarker(new MarkerOptions()
                            .position(point)
                            .title(label)
                            .icon(icon)
                            .draggable(true));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }






       // SavedLocations.places.add(label);
       // SavedLocations.arrayAdapter.notifyDataSetChanged();
        //SavedLocations.locations.add(point);


                     //   BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
    }


}