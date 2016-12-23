package com.example.kunal4.railwayviacomvid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.kunal4.railwayviacomvid.R.id.recyclerView;

/**
 * Created by kunal4 on 12/23/16.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<ListItem> items;

    Context mcon;



    public CardAdapter(Context con, String[] names, String[] ratings, Bitmap[] images, String[] links){
        super();
        items = new ArrayList<ListItem>();
        mcon = con;
        for(int i =0; i<names.length; i++){
            ListItem item = new ListItem();
            item.setName(names[i]);
            item.setRating(ratings[i]);
            item.setImage(images[i]);
            item.setLink(links[i]);
            items.add(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_video_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem list =  items.get(position);
        holder.imageView.setImageBitmap(list.getImage());
        holder.textViewName.setText(list.getName());
        holder.textViewRating.setText("Rating: "+list.getRating());

//        holder.textViewName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mcon, VideoPlayerActivity.class);
//                intent.putExtra("LINK",list.getLink());
//                mcon.startActivity(intent);
//            }
//        });



    }





    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewRating;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewRating = (TextView) itemView.findViewById(R.id.textViewRating);

        }
    }
}


