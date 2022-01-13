package com.global.addverb_tech_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class displayRegionAdapter extends RecyclerView.Adapter<displayRegionAdapter.displayRegionHolder> {

    private List<DisplayRegionModel> displayRegionModelArrayList = new ArrayList<>();
    private Context context;

    public displayRegionAdapter(Context context, List<DisplayRegionModel> displayRegionModelArrayList) {
        this.displayRegionModelArrayList = displayRegionModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public displayRegionAdapter.displayRegionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.display_region_list_item, parent, false);
        return new displayRegionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull displayRegionAdapter.displayRegionHolder holder, int position) {

        // setting text in the textview
        holder.textViewName.setText(displayRegionModelArrayList.get(position).getCommon());
        holder.textViewCapital.setText(displayRegionModelArrayList.get(position).getCapital());
        holder.textViewLanguage.setText(displayRegionModelArrayList.get(position).getLanguages());
        holder.textViewBorder.setText(displayRegionModelArrayList.get(position).getBorders());
        holder.textViewRegion.setText(displayRegionModelArrayList.get(position).getRegion());
        holder.textViewSubRegion.setText(displayRegionModelArrayList.get(position).getSubregion());
        holder.textViewPopulation.setText(displayRegionModelArrayList.get(position).getPopulation());
        Glide.with(context).load(displayRegionModelArrayList.get(position).getPng()).into(holder.categoryImage);

    }

    @Override
    public int getItemCount() {
        return displayRegionModelArrayList.size();
    }

    public class displayRegionHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewCapital;
        private TextView textViewLanguage;
        private TextView textViewBorder;
        private TextView textViewRegion;
        private TextView textViewSubRegion;
        private TextView textViewPopulation;
        private ImageView categoryImage;

        public displayRegionHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCapital = itemView.findViewById(R.id.textViewCapital);
            textViewLanguage = itemView.findViewById(R.id.textViewLanguages);
            textViewBorder = itemView.findViewById(R.id.textViewBorders);
            textViewRegion = itemView.findViewById(R.id.textViewRegion);
            textViewSubRegion = itemView.findViewById(R.id.textViewSubRegion);
            textViewPopulation = itemView.findViewById(R.id.textViewPopulation);
            categoryImage = itemView.findViewById(R.id.categoryImage);

        }
    }
}
