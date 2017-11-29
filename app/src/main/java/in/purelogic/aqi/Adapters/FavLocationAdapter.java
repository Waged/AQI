package in.purelogic.aqi.Adapters;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.purelogic.aqi.Database.AppDatabase;
import in.purelogic.aqi.Database.DetailLocation;
import in.purelogic.aqi.Database.DetailLocationDao;
import in.purelogic.aqi.PlaceRecord;
import in.purelogic.aqi.R;
import in.purelogic.aqi.activities.MapsActivity;


public class FavLocationAdapter extends RecyclerView.Adapter<FavLocationAdapter.FavLocationViewHolder> {

    private List<DetailLocation> detailLocationList;

    Context context;
    AppDatabase db ;


    // Provide a suitable constructor (depends on the kind of dataset)
    public FavLocationAdapter(List<DetailLocation> detailLocationList) {
        this.detailLocationList = detailLocationList;


    }

    @Override
    public FavLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        context = parent.getContext();
        db = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME).build();
        return new FavLocationViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(FavLocationViewHolder holder, final int position) {


        holder.myFavLoc.setText(detailLocationList.get(position).getLocationName());
        holder.setPosition(position);
        int aqiLevel = detailLocationList.get(position).getAqiLevel();
        if (aqiLevel >= 0 && aqiLevel < 50) {
            //  holder.ivAqi.setBackgroundColor(ContextCompat.getColor(context,R.color.good));
            //  holder.ivAqi.setBackgroundColor(Color.parseColor("#6c12af63"));
            holder.ivAqi.setImageResource(R.drawable.green_list);
        } else if (aqiLevel >= 50 && aqiLevel < 100) {
            //holder.ivAqi.setBackgroundColor(ContextCompat.getColor(context,R.color.moderate));
            // holder.ivAqi.setBackgroundColor(Color.parseColor("#56b905"));
            holder.ivAqi.setImageResource(R.drawable.yellow_list);
        } else if (aqiLevel >= 100 && aqiLevel < 200) {
            // holder.ivAqi.setBackgroundColor(ContextCompat.getColor(context,R.color.bad));
            // holder.ivAqi.setBackgroundColor(Color.parseColor("#e3180a"));
            holder.ivAqi.setImageResource(R.drawable.orange_list);
        } else if (aqiLevel >= 200) {
            //     holder.ivAqi.setBackgroundColor(ContextCompat.getColor(context,R.color.hazardous));
            //  holder.ivAqi.setBackgroundColor(Color.parseColor("#8B38FF"));
            holder.ivAqi.setImageResource(R.drawable.red_list);
        } else {
            holder.ivAqi.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey));
            // holder.ivAqi.setBackgroundColor(Color.parseColor("#dfd6d9"));
        }
        holder.aqi.setText(detailLocationList.get(position).getAqiLevel() + "");
        holder.temp.setText(detailLocationList.get(position).getTemprature() + "");
        holder.humid.setText(detailLocationList.get(position).getHumidity() + "");


    }

    @Override
    public int getItemCount() {
        return detailLocationList.size();
    }


    public class FavLocationViewHolder extends RecyclerView.ViewHolder {
        TextView myFavLoc, aqi, humid, temp;
        ImageButton removePlace;
        ImageView ivAqi;

        int position;

        public void setPosition(int position) {
            this.position = position;
        }

        public FavLocationViewHolder(final View itemView) {
            super(itemView);
            myFavLoc = (TextView) itemView.findViewById(R.id.map_tvMyLocation);
            aqi = (TextView) itemView.findViewById(R.id.map_tvAqi);
            ivAqi = (ImageView) itemView.findViewById(R.id.ivAqiLevel);
            temp = (TextView) itemView.findViewById(R.id.map_tvTemp);
            humid = (TextView) itemView.findViewById(R.id.map_tvHumid);
            removePlace = (ImageButton) itemView.findViewById(R.id.ibRemove);

            removePlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    new AsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... params) {
                            DetailLocationDao ldd = db.getDetailLocationDao();
                                ldd.deleteAll(detailLocationList.get(position));
                                return true;
                        }
                        @Override
                        protected void onPostExecute(Boolean fav) {
                            super.onPostExecute(fav);
                            if (fav) {
                                detailLocationList.remove(position);
                                notifyDataSetChanged();
                            }

                        }
                    }.execute();
                }
            });
        }
    }
}