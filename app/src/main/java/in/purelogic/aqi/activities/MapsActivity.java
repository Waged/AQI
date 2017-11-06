package in.purelogic.aqi.activities;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.purelogic.aqi.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.map_card_view)
    CardView myMapCard;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
            myLocation = bundle.getString("location");
            aqi = bundle.getString("aqi");
            tvLocation.setText(myLocation);
        }
        //tvAqi.setText(aqi);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    /*
    @OnClick(R.id.ivFav)
    public void addToFavourites(View v){
        Toast.makeText(this,"not-Captured", Toast.LENGTH_SHORT);

    }*/

    public void addToFav(View v){
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        if(!isFavourite) {
            ivAddToFav.setImageResource(R.drawable.ic_favorites);
            isFavourite=true;
        }else{
            ivAddToFav.setImageResource(R.drawable.ic_notinfavorites);
            isFavourite=false;
        }
    }
}
