package in.purelogic.aqi.activities;


import android.arch.persistence.room.Room;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;

import android.location.LocationManager;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
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
import in.purelogic.aqi.Database.AppDatabase;
import in.purelogic.aqi.Database.DetailLocation;
import in.purelogic.aqi.Database.DetailLocationDao;


import in.purelogic.aqi.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener,PlaceSelectionListener {


    @BindView(R.id.map_tvMyLocation)
    TextView tvLocation;

    @BindView(R.id.ivFav)
    ImageButton ivAddToFav;

    @BindView(R.id.map_tvTemp)
    TextView tvTemp;

    @BindView(R.id.map_tvHumid)
    TextView tvHumi;

    @BindView(R.id.maps_tvAqi)
    TextView tvAqi;
   // @BindView(R.id.autocomplete_fragment)
   // PlaceAutocompleteFragment autocompleteFragment;

    // @BindView(R.id.floating_search_view)
   // FloatingSearchView floatingSearchView;

    private GoogleMap mMap;
    String myLocation;
    PlaceAutocompleteFragment autocompleteFragment;
    LatLng myPlace;
    double latitude;
    double longitude;
    boolean isFavourite = false;
    String location = "NA";
    String knownName = null;
    int aqi = 0;
    int humidity = 0;
    int temprature = 0;
    LocationManager locationManager;
    String provider;
    Bundle bundle;
    BitmapDescriptor icon;
    Marker m = null;
    private AppDatabase db;
    DetailLocation detailLocation;
    String searchedCity;

    @Override
    protected void onPause() {
        super.onPause();
        //   locationManager.removeUpdates((LocationListener) MapsActivity.this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        bundle = getIntent().getExtras();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Retrieve the PlaceAutocompleteFragment.
         autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(MapsActivity.this);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("search", "Place: " + place.getName());
                Toast.makeText(MapsActivity.this, place.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("search", "An error occurred: " + status);
                Toast.makeText(MapsActivity.this, status.toString(), Toast.LENGTH_SHORT).show();
            }
        });

/*
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                //get suggestions based on newQuery
                Geocoder gc = new Geocoder(MapsActivity.this);
                if (gc.isPresent()) {
                    List<Address> list = null;
                    try {
                        list = gc.getFromLocationName(newQuery, 1);
                       // floatingSearchView.setSu
                     //   Toast.makeText(MapsActivity.this, list.get(0)+"", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = list.get(0);
                    double lat = address.getLatitude();
                    double lng = address.getLongitude();


                    //pass them on to the search view
                 //   floatingSearchView.swapSuggestions (list);
                }
            }
        });*/
        // Intent i = getIntent();
        // int location = i.getIntExtra("locationInfo", -1);

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
        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();

        if (bundle != null) {
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
            myPlace = new LatLng(latitude, longitude);
            location = bundle.getString("location");
            aqi = bundle.getInt("aqi");
            humidity = bundle.getInt("humidity");
            temprature = bundle.getInt("temperature");
            knownName = bundle.getString("knownname");
            searchedCity = bundle.getString("searchedtext");
            detailLocation = new DetailLocation();
            detailLocation.setLocationName(location);
            detailLocation.setAqiLevel(aqi);
            detailLocation.setHumidity(humidity);
            detailLocation.setTemprature(temprature);
            tvLocation.setText(location);
            tvHumi.setText(Integer.toString(humidity));
            tvTemp.setText(Integer.toString(temprature));
            tvAqi.setText(Integer.toString(aqi));
            addMyMarker(aqi);

        } else {
            //delhi in map
            LatLng delhiPlace = new LatLng(28.7041, 77.1025);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(delhiPlace));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(8));
            tvLocation.setText("NA");
            Toasty.error(this, "GPS or Connection problem !", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "No place Provided check GPS", Toast.LENGTH_SHORT).show();
        }


    }

    private void addMyMarker(int aqi) {
        if (aqi > 0 && aqi <= 50) {
            //good
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

            mMap.addMarker(new MarkerOptions().position(myPlace).title(location).icon(icon).snippet("Current Location! "));

            for (int rad = 100; rad <= 500; rad += 10) {
                CircleOptions circleOptions = new CircleOptions()
                        .center(new LatLng(latitude, longitude))   //set center
                        .radius(rad)   //set radius in meters
                        .fillColor(Color.TRANSPARENT)  //default
                        .strokeColor(Color.GREEN)
                        .strokeWidth(2);
                mMap.addCircle(circleOptions);
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_icon);


        } else if (aqi > 50 && aqi <= 100) {
            //satisfied
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
            mMap.addMarker(new MarkerOptions().position(myPlace).title(location).icon(icon).snippet("Current Location! "));
            for (int rad = 100; rad <= 500; rad += 10) {
                CircleOptions circleOptions = new CircleOptions()
                        .center(new LatLng(latitude, longitude))   //set center
                        .radius(rad)   //set radius in meters
                        .fillColor(Color.TRANSPARENT)  //default
                        .strokeColor(Color.CYAN)
                        .strokeWidth(2);
                mMap.addCircle(circleOptions);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_icon);

        } else if (aqi > 100 && aqi <= 200) {
            //bad
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            mMap.addMarker(new MarkerOptions().position(myPlace).title(location).icon(icon).snippet("Current Location! "));
            for (int rad = 100; rad <= 500; rad += 10) {
                CircleOptions circleOptions = new CircleOptions()
                        .center(new LatLng(latitude, longitude))   //set center
                        .radius(rad)   //set radius in meters
                        .fillColor(Color.TRANSPARENT)  //default
                        .strokeColor(Color.YELLOW)
                        .strokeWidth(2);
                mMap.addCircle(circleOptions);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_icon);

        } else if (aqi > 200 && aqi <= 300) {
            //very bad
            icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_icon);
            mMap.addMarker(new MarkerOptions().position(myPlace).title(location).icon(icon).snippet("Current Location! "));
            for (int rad = 100; rad <= 500; rad += 10) {
                CircleOptions circleOptions = new CircleOptions()
                        .center(new LatLng(latitude, longitude))   //set center
                        .radius(rad)   //set radius in meters
                        .fillColor(Color.TRANSPARENT)  //default
                        .strokeColor(Color.RED)
                        .strokeWidth(2);
                mMap.addCircle(circleOptions);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_icon);

        } else if (aqi > 300 && aqi <= 500) {
            //hazardeous
            icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_icon);
            mMap.addMarker(new MarkerOptions().position(myPlace).title(location).icon(icon).snippet("Current Location! "));
            for (int rad = 100; rad <= 500; rad += 10) {
                CircleOptions circleOptions = new CircleOptions()
                        .center(new LatLng(latitude, longitude))   //set center
                        .radius(rad)   //set radius in meters
                        .fillColor(Color.TRANSPARENT)  //default
                        .strokeColor(Color.RED)
                        .strokeWidth(2);
                mMap.addCircle(circleOptions);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

    }


    @OnClick(R.id.ivFav)
    public void addToFavourites() {


        // if (!isFavourite) {
        //     isFavourite = true;

        new AsyncTask<Void, Void, Boolean>() {
            Animation fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

            @Override
            protected Boolean doInBackground(Void... params) {
                DetailLocationDao ldd = db.getDetailLocationDao();
                List<DetailLocation> locationsList = db.getDetailLocationDao().getAll();
                String placeName = null;
                if (locationsList.size() == 0) {
                    ldd.insertAll(detailLocation);
                    return true;
                } else {
                    for (int i = 0; i < locationsList.size(); i++) {
                        placeName = locationsList.get(i).getLocationName();
                        if (!placeName.contains(knownName)) {
                            ldd.insertAll(detailLocation);
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean fav) {
                super.onPostExecute(fav);
                if (fav) {
                    Toast.makeText(MapsActivity.this, "Added to Favourites!", Toast.LENGTH_SHORT).show();
                    ivAddToFav.setImageResource(R.drawable.ic_favorites);
                    ivAddToFav.setAnimation(fade);
                    ivAddToFav.setVisibility(View.INVISIBLE);

                } else {
                    Toast.makeText(MapsActivity.this, "Already Added", Toast.LENGTH_SHORT).show();
                    ivAddToFav.setImageResource(R.drawable.ic_favorites);
                    ivAddToFav.setAnimation(fade);
                    ivAddToFav.setVisibility(View.INVISIBLE);

                }
            }
        }.execute();

    }
        /*
        else {
            isFavourite = false;
            ivAddToFav.setImageResource(R.drawable.ic_notinfavorites);
            new AsyncTask<Void, Void, Void>() {
              @Override
                protected Void doInBackground(Void... params) {
                    DetailLocationDao ldd = db.getDetailLocationDao();
                    List<DetailLocation> notes = db.getDetailLocationDao().getAll();
                    for(int i = 0 ; i<notes.size();i++){
                        String deletedData = notes.get(i).getLocationName();
                        if(deletedData.equals(detailLocation.getLocationName())){
                            ldd.deleteAll(notes.get(i));
                        }
                    }
                    return null ;
                }
                @Override
                protected void onPostExecute(Void v) {
                    Toast.makeText(MapsActivity.this, "Removed from favourites", Toast.LENGTH_SHORT).show();
                }
            }.execute();

        }
    }*/

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


    }

    @Override
    public void onPlaceSelected(Place place) {

        Log.e("search",place.getName().toString());


        // Format the returned place's details and display them in the TextView.
      //  mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(), place.getId(),
         //       place.getAddress(), place.getPhoneNumber(), place.getWebsiteUri()));

     //   CharSequence attributions = place.getAttributions();
      //  if (!TextUtils.isEmpty(attributions)) {
           // mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
       // } else {
          //  mPlaceAttribution.setText("");
       // }

    }

    @Override
    public void onError(Status status) {
        Log.e("search", "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();

    }


}