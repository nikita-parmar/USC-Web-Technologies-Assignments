package com.example.nikitaparmar.eventsearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class favListAdapter extends RecyclerView.Adapter<favListAdapter.ViewHolder> {
    private Context context;
    private Map<String, ?> list;
    private List<String> keys = new ArrayList<>();

    public favListAdapter(Context pcontext, Map<String, ?> plist) {
        context = pcontext;
        list = plist;
        keys.addAll(list.keySet());
    }

    @NonNull
    @Override
    public favListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.an_event_fav, viewGroup, false);
        return new favListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favListAdapter.ViewHolder holder, int i) {
            String value = (String)list.get(keys.get(i));
            System.out.println("my list: "+list.get(keys.get(i)));
            String[] eventData = value.split("!!");

            holder.feventNameId.setText(eventData[0]);
            holder.fvenueNameId.setText(eventData[3]);
            holder.fdateTimeId.setText(eventData[2]);

            if(eventData[1].equals("Sports")){
                holder.fcategoryId.setImageResource(R.drawable.sport_icon);
            }else if(eventData[1].equals("Music")){
                holder.fcategoryId.setImageResource(R.drawable.music_icon);
            }else if (eventData[1].equals("Film")){
                holder.fcategoryId.setImageResource(R.drawable.film_icon);
            }else if (eventData[1].equals("Miscellaneous")){
                holder.fcategoryId.setImageResource(R.drawable.miscellaneous_icon);
            }else{
                holder.fcategoryId.setImageResource(R.drawable.art_icon);
            }
            holder.ffavImgId.setImageResource(R.drawable.ic_favorite_heart_red_24dp);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView feventNameId;
        public TextView fvenueNameId;
        public TextView fdateTimeId;
        public ImageView fcategoryId;
        public ImageView ffavImgId;
        public LinearLayout feachEventClickableId;

        public ViewHolder(View view) {
            super(view);
            feventNameId = view.findViewById(R.id.feventNameId);
            fvenueNameId = view.findViewById(R.id.fvenueNameId);
            fdateTimeId = view.findViewById(R.id.fdateTimeId);
            fcategoryId = view.findViewById(R.id.fimgCategoryId);

            ffavImgId = view.findViewById(R.id.ffavImgId);
            ffavImgId.setOnClickListener(this);

            feachEventClickableId = view.findViewById((R.id.feachEventClickableId));
            feachEventClickableId.setOnClickListener(this);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String elementId = keys.get(getAdapterPosition());
            System.out.println("elementID: "+elementId);
            String value = (String)list.get(keys.get(getAdapterPosition()));
            //IF FAVORITE IS CLICKED
            if(view.getId() == ffavImgId.getId()){
                SharedPreferences varsharedPref;
                String filenameSharedPref = "SharedPreferenceFile";
                varsharedPref = context.getSharedPreferences(filenameSharedPref, Context.MODE_PRIVATE);
                SharedPreferences.Editor editorSharedPref = varsharedPref.edit();
                //REMOVE FROM FAV
                editorSharedPref.remove(elementId);
                editorSharedPref.commit();
                list = varsharedPref.getAll();
                System.out.println("get all shared pref into list: : "+ list);
                notifyDataSetChanged();
//                RecyclerView favList = view.findViewById(R.id.feventListRV);
//                  favListAdapter fadapter = new favListAdapter(context, varsharedPref.getAll());
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, linearLayoutManager.getOrientation());
//                favList.setHasFixedSize(true);
//                favList.setLayoutManager(linearLayoutManager);
//                favList.addItemDecoration(dividerItemDecoration);
//                favList.setAdapter(fadapter);

  //              fadapter.notifyDataSetChanged();


            }else if(view.getId() == feachEventClickableId.getId()){
                    System.out.println("Layout is clicked");
                    Intent i = new Intent(context, eventDetails4.class);
                    i.putExtra("eventId",elementId );
                    ((Activity)context).startActivity(i);

            }
        }
    }
}

