package in.purelogic.aqi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.purelogic.aqi.Adapters.FavLocationAdapter;
import in.purelogic.aqi.PlaceRecord;
import in.purelogic.aqi.R;

public class SavedLocations extends AppCompatActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;



    private List<PlaceRecord> placeRecords = new ArrayList<>();
    private FavLocationAdapter mAdapter;

    @BindView(R.id.btnAddNewFav)
    Button btnAddNewFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_locations);
        ButterKnife.bind(this);

        btnAddNewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SavedLocations.this, "We will redirect to Maps !", Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter = new FavLocationAdapter(placeRecords);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        initPlacesData();
    }

    private void initPlacesData() {
        PlaceRecord placeRecord = new PlaceRecord( "Rithala,Delhi","Good Air ", "27",  "50%", "103");
        placeRecords.add(placeRecord);

        placeRecord = new PlaceRecord("Rohini,Delhi","Good", "27",  "47%", "103");
        placeRecords.add(placeRecord);

        placeRecord = new PlaceRecord("Pitampura,Delhi","Good", "27", "47%","103");
        placeRecords.add(placeRecord);

        placeRecord = new PlaceRecord("NSP,Delhi","Good", "28", "47%","103");
        placeRecords.add(placeRecord);

        placeRecord = new PlaceRecord("Rajiv Chawk,Delhi","Good", "29", "47%","103");
        placeRecords.add(placeRecord);

        mAdapter.notifyDataSetChanged();


    }

}
