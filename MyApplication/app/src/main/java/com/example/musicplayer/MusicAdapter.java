package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    private static ArrayList<Music> mDataset;
    private Context c;

    public MusicAdapter(Context c, ArrayList<Music> mDataset) {
        this.c =c;
        this.mDataset = mDataset;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemArtist;
        public TextView itemDuration;
        public ImageView itamImage;
        public ImageButton deleteButton;

        public MyViewHolder(View v) {
            super(v);
            itemName = (TextView) v.findViewById(R.id.itemName);
            itemArtist = (TextView) v.findViewById(R.id.itemArtist);
            itemDuration = (TextView) v.findViewById(R.id.itemDuration);
            itamImage = (ImageView) v.findViewById(R.id.itemImage);
            deleteButton = (ImageButton) v.findViewById(R.id.deleteButton);
        }
    }

    // adaptör oluşturulduğunda viewholder'ın başlatmasını sağlar
    @Override
    public MusicAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // onCreateViewHolder'dan dönen verileri bağlama işlemi
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Music selectedItem = mDataset.get(position);
        holder.itemName.setText(selectedItem.getName());
        holder.itemArtist.setText(selectedItem.getArtist_name());
        holder.itemDuration.setText(selectedItem.getDuration());


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataset.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),mDataset.size());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, Player.class);
                intent.putExtra("Position", holder.getPosition());
                intent.putExtra("Size", getItemCount());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static Music getItem(int position) {
        return mDataset.get(position);
    }

}