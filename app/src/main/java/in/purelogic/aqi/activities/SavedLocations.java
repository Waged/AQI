package in.purelogic.aqi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.purelogic.aqi.R;

public class SavedLocations extends AppCompatActivity {


    static ArrayList<String> places;
    static ArrayAdapter arrayAdapter;
    static ArrayList<LatLng> locations;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.list_view)
    RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_locations);
        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        places = new ArrayList<>();
        places.add("Add a new place...");
        locations = new ArrayList<>();
        locations.add(new LatLng(0, 0));
        Log.i("Places", places.toString());
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        recyclerView.setAdapter();



    }
}
