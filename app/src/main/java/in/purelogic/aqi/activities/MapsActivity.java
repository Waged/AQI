package in.purelogic.aqi.activities;

import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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



/*
    @Override
    protected void onResume() {
        super.onResume();

        setUpMapIfNeeded();

        if (location == -1 || location == 0) {

            locationManager.requestLocationUpdates(provider, 400, 1, this);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }

    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
            myLocation = bundle.getString("location");
            aqi = bundle.getString("aqi");
            tvLocation.setText(myLocation);
        }
        else {
            Intent back = new Intent(this , MainActivity.class);
            Toast.makeText(this, "No place Provided check GPS", Toast.LENGTH_SHORT).show();
            startActivity(back);
        }
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

        // Add a marker in Sydney and move the camera
        LatLng myPlace = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(myPlace).title("Your Place "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }


    @OnClick(R.id.ivFav)
    public void addToFavourites(View v) {
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

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        String label = new Date().toString();

        try {
            List<Address> listAddresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
            if (listAddresses != null && listAddresses.size() > 0) {
                label = listAddresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       // SavedLocations.places.add(label);
       // SavedLocations.arrayAdapter.notifyDataSetChanged();
        //SavedLocations.locations.add(point);

        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title(label)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }


}