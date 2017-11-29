package in.purelogic.aqi.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
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
import in.purelogic.aqi.Database.AppDatabase;
import in.purelogic.aqi.Database.DetailLocation;
import in.purelogic.aqi.Database.DetailLocationDao;

import in.purelogic.aqi.PlaceRecord;
import in.purelogic.aqi.R;

public class SavedLocations extends AppCompatActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    AppDatabase db ;


    private List<DetailLocation> detailLocations = new ArrayList<>();
    private FavLocationAdapter mAdapter;

    @BindView(R.id.btnAddNewFav)
    Button btnAddNewFav;

    @BindView(R.id.ibBack)
    ImageButton btnBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_locations);
        ButterKnife.bind(this);
        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SavedLocations.this,MainActivity.class));
            }
        });
        btnAddNewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SavedLocations.this, "We will redirect to Maps !", Toast.LENGTH_SHORT).show();
            }
        });


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initPlacesData();


    }

    private void initPlacesData() {

        new AsyncTask<Void, Void, List<DetailLocation>>() {
            @Override
            protected List<DetailLocation> doInBackground(Void... params) {
                DetailLocationDao ld = db.getDetailLocationDao();
                detailLocations= ld.getAll();
                return ld.getAll();
            }
            @Override
            protected void onPostExecute(List<DetailLocation> locations) {

                if(locations != null){
                    mAdapter = new FavLocationAdapter(locations);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(SavedLocations.this, "No Favourites Added Yet!", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();


    }

}
