package in.purelogic.aqi.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import in.purelogic.aqi.PlaceRecord;
import in.purelogic.aqi.R;


public class FavLocationAdapter extends RecyclerView.Adapter<FavLocationAdapter.FavLocationViewHolder> {

    private List<PlaceRecord> placeRecordList ;


    // Provide a suitable constructor (depends on the kind of dataset)
    public FavLocationAdapter(List<PlaceRecord> placeRecordList) {
        this.placeRecordList = placeRecordList;

    }

    @Override
    public FavLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new FavLocationViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(FavLocationViewHolder holder, int position) {
        holder.myFavLoc.setText(placeRecordList.get(position).getLocationName());
        holder.pm25.setText(placeRecordList.get(position).getPm25());
        holder.temp.setText(placeRecordList.get(position).getTemp());
        holder.humid.setText(placeRecordList.get(position).getHumid());

    }

    @Override
    public int getItemCount() {
        return placeRecordList.size();
    }


    public class FavLocationViewHolder extends RecyclerView.ViewHolder {
        public TextView myFavLoc,pm25,humid,temp;
        public ImageButton removePlace;

        public FavLocationViewHolder(View itemView) {
            super(itemView);
            myFavLoc = (TextView)itemView.findViewById(R.id.map_tvMyLocation);
            pm25 = (TextView)itemView.findViewById(R.id.map_tvPm25);
            temp = (TextView)itemView.findViewById(R.id.map_tvTemp);
            humid = (TextView)itemView.findViewById(R.id.map_tvHumid);
            removePlace= (ImageButton)itemView.findViewById(R.id.ibRemove);

        }
    }
}