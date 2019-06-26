package com.example.nikitaparmar.eventsearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static java.security.AccessController.getContext;

public class eventListAdapter extends RecyclerView.Adapter<eventListAdapter.ViewHolder> {

    private Context context;
    private List<EventListClass> list;

    public eventListAdapter(Context pcontext, List<EventListClass> plist) {
        context = pcontext;
        list = plist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.an_event_eventlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EventListClass eventlistObj = list.get(position);

        holder.eventNameId.setText(String.valueOf(eventlistObj.getEventName()));
        holder.venueNameId.setText(String.valueOf(eventlistObj.getVenueName()));
        holder.dateTimeId.setText(eventlistObj.getDateTime());

        if(eventlistObj.getCategory().equals("Sports")){
            holder.categoryId.setImageResource(R.drawable.sport_icon);
        }else if(eventlistObj.getCategory().equals("Music")){
            holder.categoryId.setImageResource(R.drawable.music_icon);
        }else if (eventlistObj.getCategory().equals("Film")){
            holder.categoryId.setImageResource(R.drawable.film_icon);
        }else if (eventlistObj.getCategory().equals("Miscellaneous")){
            holder.categoryId.setImageResource(R.drawable.miscellaneous_icon);
        }else{
            holder.categoryId.setImageResource(R.drawable.art_icon);
        }

        SharedPreferences varsharedPref;
        String filenameSharedPref = "SharedPreferenceFile";
        varsharedPref = context.getSharedPreferences(filenameSharedPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorSharedPref = varsharedPref.edit();
        if( varsharedPref.getString(eventlistObj.getEid(), null) == null){
            holder.favImgId.setImageResource(R.drawable.ic_favorite_heart_border_black_24dp);
        }else{
            holder.favImgId.setImageResource(R.drawable.ic_favorite_heart_red_24dp);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        //Put it in method if possible
        public TextView eventNameId;
        public TextView venueNameId;
        public TextView dateTimeId;
        public ImageView categoryId;
        public ImageView favImgId;
        public LinearLayout clickLLId;
        public LinearLayout eachEventClickableId;
        public View textEntryView;

        public ViewHolder(View viewAnItem) {
            super(viewAnItem);
            eventNameId = viewAnItem.findViewById(R.id.eventNameId);
            venueNameId = viewAnItem.findViewById(R.id.venueNameId);
            dateTimeId = viewAnItem.findViewById(R.id.dateTimeId);
            categoryId = viewAnItem.findViewById(R.id.imgCategoryId);

            favImgId = viewAnItem.findViewById(R.id.favImgId);
            favImgId.setOnClickListener(this);

            eachEventClickableId = viewAnItem.findViewById((R.id.eachEventClickableId));
            eachEventClickableId.setOnClickListener(this);

            viewAnItem.setOnClickListener(this);
      }

        @Override
        public void onClick(View view) {
            EventListClass eventListObj = list.get(getAdapterPosition());

            //IF FAVORITE IS CLICKED
            if(view.getId() == favImgId.getId()){
                    Drawable.ConstantState setone = favImgId.getDrawable().getConstantState();
                    Drawable.ConstantState border = context.getResources().getDrawable(R.drawable.ic_favorite_heart_border_black_24dp).getConstantState();
                    Drawable.ConstantState red = context.getResources().getDrawable(R.drawable.ic_favorite_heart_red_24dp).getConstantState();

                    SharedPreferences varsharedPref;
                    String filenameSharedPref = "SharedPreferenceFile";
                    varsharedPref = context.getSharedPreferences(filenameSharedPref, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorSharedPref = varsharedPref.edit();
                   //editorSharedPref.clear();

                   //ADD TO FAV
                    if(setone.equals(border)){
                        System.out.println("Black Heart");
                        favImgId.setImageResource(R.drawable.ic_favorite_heart_red_24dp);

                        String s = eventListObj.getEventName() +"!!"+ eventListObj.getCategory() +"!!"+ eventListObj.getDateTime() +"!!"+ eventListObj.getVenueName();
                        editorSharedPref.putString(eventListObj.getEid(), s);
                        editorSharedPref.commit();
                        System.out.println("Size in add in eventlist: "+varsharedPref.getAll().size());

                        Toast toast = Toast.makeText(context, eventListObj.getEventName()+" added to favorites", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    //REMOVE FROM FAV
                    else{
                        editorSharedPref.remove(eventListObj.getEid());
                        editorSharedPref.commit();
                        String s = varsharedPref.getString(eventListObj.getEid(), "None found");
                        System.out.println("Printing deleted item: "+s);
                        System.out.println("Size in delete in eventlist: "+varsharedPref.getAll().size());

                        System.out.println("Red Heart");
                        favImgId.setImageResource(R.drawable.ic_favorite_heart_border_black_24dp);

                        Toast toast = Toast.makeText(context, eventListObj.getEventName()+" removed from favorites", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }else if(view.getId() == eachEventClickableId.getId()){

                    System.out.println("Layout is clicked");
                    final Intent i = new Intent(context, eventDetails4.class);
                    i.putExtra("eventId",eventListObj.getEid());
                    i.putExtra("category", eventListObj.getCategory());
                    i.putExtra("venuename", eventListObj.getVenueName());

                RequestQueue reqqueue = Volley.newRequestQueue(context);
                String eventDetailsUrl = "http://homework8-projec-1541555481784.appspot.com/eventdetail/"+eventListObj.getEid();
                System.out.println("First url: "+eventDetailsUrl);
                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, eventDetailsUrl,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println("In try");
                                    String venuename2 = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name");
                                    System.out.println("in try venuename is "+venuename2);
                                    i.putExtra("venuename2",venuename2);
                                    String artist1 = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("attractions").getJSONObject(0).getString("name");
                                    i.putExtra("artistname1", artist1);
                                    try{
                                        String artist2 = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("attractions").getJSONObject(1).getString("name");
                                        i.putExtra("artistname2", artist2);
                                    }catch(Exception e){
                                        System.out.println("No second artist");
                                    }
                                    ((Activity)context).startActivity(i);
                                } catch (Exception e) {
                                    System.out.println("In catch to get venuename");

                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  mTextView.setText("That didn't work!");
                    }
                });
                reqqueue.add(stringRequest);

                }
            }
        }
    }

