package fr.wcs.wcstravel;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.wcs.wcstravel.Model.TravelModel;

/**
 * Created by apprenti on 24/01/18.
 */

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {
    private List<TravelModel> mTravel;
     Activity activity;

    public TravelAdapter(List<TravelModel> mTravel, Activity activity){
        this.mTravel = mTravel;
        this.activity = activity;
    }

    @Override
    public TravelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_fly_item, parent, false);

        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(TravelAdapter.ViewHolder holder, int position) {
        holder.display(mTravel.get(position));
    }

    @Override
    public int getItemCount() {
        return mTravel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView resultComp;
        TextView resultDateDep;
        TextView resultDateDes;
        TextView resultPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            resultComp = itemView.findViewById(R.id.resultComp);
            resultDateDep = itemView.findViewById(R.id.resultDateDep);
            resultDateDes = itemView.findViewById(R.id.resultDateDes);
            resultPrice = itemView.findViewById(R.id.resultPrice);
        }
        public void display(final TravelModel travlWorld){

            resultComp.setText(travlWorld.getAirline());
            resultDateDep.setText(travlWorld.getDeparture_date());
            resultDateDes.setText(travlWorld.getReturn_date());
            resultPrice.setText(travlWorld.getPrice());
        }
    }
}
