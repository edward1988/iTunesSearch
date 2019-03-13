package com.ediksarkis.msttest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ediksarkis.msttest.R;
import com.ediksarkis.msttest.network.model.ItunesContent;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItunesAdapter extends RecyclerView.Adapter<ItunesAdapter.MyViewHolder>{

    private Context context;
    private List<ItunesContent> itunesContent;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView artistName, collectionName;
        private ImageView thumbnail;

        private MyViewHolder(View view) {
            super(view);
            artistName = view.findViewById(R.id.artistName);
            collectionName = view.findViewById(R.id.collectionName);
            thumbnail = view.findViewById(R.id.thumbnail);

        }
    }


    public ItunesAdapter(Context context, List<ItunesContent> itunesContent) {
        this.context = context;
        this.itunesContent = itunesContent;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itunes_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.artistName.setText(itunesContent.get(position).getArtistName());
        holder.collectionName.setText(itunesContent.get(position).getCollectionName());
        Picasso.get().load(itunesContent.get(position).getCollectionImage()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return itunesContent.size();
    }

}
