package com.example.androidsqlitepracticepart2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsqlitepracticepart2.room.Locations;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private Context context;
    private List<Locations> locations;
    private ItemClickListener itemClickListener;

    public  interface  ItemClickListener{
        void onClick(View view, int pos);
    }

    public LocationAdapter(Context context, List<Locations> locations) {
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_location,parent,false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder holder, int position) {
        holder.txtLocation.setText(context.getString(R.string.text_location,position+1
                ,locations.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void setLocations(List<Locations> locations) {
        this.locations = locations;
        this.notifyDataSetChanged();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtLocation;
        ImageView btnEdit, btnDelete;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLocation = itemView.findViewById(R.id.text_location);
            btnDelete = itemView.findViewById(R.id.button_delete);
            btnEdit = itemView.findViewById(R.id.button_edit);

            btnEdit.setOnClickListener(this);
            btnDelete.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition());
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
