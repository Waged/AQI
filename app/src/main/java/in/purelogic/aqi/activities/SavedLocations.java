package in.purelogic.aqi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.purelogic.aqi.R;

public class SavedLocations extends AppCompatActivity {


    static ArrayList<String> places;
    static ArrayAdapter arrayAdapter;
    static ArrayList<LatLng> locations;


    @BindView(R.id.list_view)
    ListView listView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_locations);
        ButterKnife.bind(this);
        places = new ArrayList<>();
        places.add("Add a new place...");
        locations = new ArrayList<>();
        locations.add(new LatLng(0, 0));
        Log.i("Places", places.toString());
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("locationInfo", position);
                startActivity(i);
            }

        });


    }
}
