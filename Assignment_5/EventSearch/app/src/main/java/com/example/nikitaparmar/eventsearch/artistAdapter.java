package com.example.nikitaparmar.eventsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class artistAdapter extends RecyclerView.Adapter<artistAdapter.ViewHolder>  {
    private Context context;
    private List<ArtistsClass> list;

    public artistAdapter(Context acontext, List<ArtistsClass> alist) {
        context = acontext;
        list = alist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.an_artist_item, viewGroup, false);
        return new artistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ArtistsClass artistObj = list.get(i);
        viewHolder.artistHeaderId.setText(artistObj.getArtistHeader());

        if(!artistObj.getSname().equals("NA")){
            viewHolder.dataArtistName.setText(artistObj.getSname());
            viewHolder.spotify1.setVisibility(View.VISIBLE);
        }
        if(!artistObj.getSpopularity().equals("NA")){
            viewHolder.dataArtistPopularity.setText(artistObj.getSpopularity());
            viewHolder.spotify2.setVisibility(View.VISIBLE);
        }
        if(!artistObj.getSfollowers().equals("NA")){
            viewHolder.dataArtistName.setText(artistObj.getSname());
            viewHolder.spotify3.setVisibility(View.VISIBLE);
        }
        if(!artistObj.getScheckat().equals("NA")){
            viewHolder.dataArtistName.setText(artistObj.getSname());
            viewHolder.spotify4.setVisibility(View.VISIBLE);
        }

        viewHolder.dataArtistFollowers.setText(artistObj.getSfollowers());
//        viewHolder.dataArtistCheckat.setText(artistObj.getScheckat());
        String atagText = "<a href="+ artistObj.getScheckat() +">Spotify</a>";
        viewHolder.dataArtistCheckat.setText(Html.fromHtml(atagText));
        viewHolder.dataArtistCheckat.setMovementMethod(LinkMovementMethod.getInstance());

        Picasso.with(context).load(artistObj.getGimg1()).into(viewHolder.imgId1);
        Picasso.with(context).load(artistObj.getGimg2()).into(viewHolder.imgId2);
        Picasso.with(context).load(artistObj.getGimg3()).into(viewHolder.imgId3);
        Picasso.with(context).load(artistObj.getGimg4()).into(viewHolder.imgId4);
        Picasso.with(context).load(artistObj.getGimg5()).into(viewHolder.imgId5);
        Picasso.with(context).load(artistObj.getGimg6()).into(viewHolder.imgId6);
        Picasso.with(context).load(artistObj.getGimg7()).into(viewHolder.imgId7);
        Picasso.with(context).load(artistObj.getGimg8()).into(viewHolder.imgId8);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView artistHeaderId;
        public TextView dataArtistName;
        public TextView dataArtistFollowers;
        public TextView dataArtistPopularity;
        public TextView dataArtistCheckat;
        public ImageView imgId1;
        public ImageView imgId2;
        public ImageView imgId3;
        public ImageView imgId4;
        public ImageView imgId5;
        public ImageView imgId6;
        public ImageView imgId7;
        public ImageView imgId8;
        public LinearLayout spotify1;
        public LinearLayout spotify2;
        public LinearLayout spotify3;
        public LinearLayout spotify4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            artistHeaderId = itemView.findViewById(R.id.artistHeaderId);
            dataArtistName = itemView.findViewById(R.id.dataArtistName);
            dataArtistFollowers = itemView.findViewById(R.id.dataArtistFollowers);
            dataArtistPopularity = itemView.findViewById(R.id.dataArtistPopularity);
            dataArtistCheckat = itemView.findViewById(R.id.dataArtistCheckat);

            imgId1 = itemView.findViewById(R.id.imgId1);
            imgId2 = itemView.findViewById(R.id.imgId2);
            imgId3 = itemView.findViewById(R.id.imgId3);
            imgId4 = itemView.findViewById(R.id.imgId4);
            imgId5 = itemView.findViewById(R.id.imgId5);
            imgId6 = itemView.findViewById(R.id.imgId6);
            imgId7 = itemView.findViewById(R.id.imgId7);
            imgId8 = itemView.findViewById(R.id.imgId8);

            spotify1 = itemView.findViewById(R.id.spotify1);
            spotify2 = itemView.findViewById(R.id.spotify2);
            spotify3 = itemView.findViewById(R.id.spotify3);
            spotify4 = itemView.findViewById(R.id.spotify4);
        }
    }
}
