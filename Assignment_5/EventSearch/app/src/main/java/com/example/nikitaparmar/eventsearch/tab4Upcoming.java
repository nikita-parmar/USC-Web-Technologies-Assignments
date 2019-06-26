package com.example.nikitaparmar.eventsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class tab4Upcoming extends Fragment {
    private RecyclerView upcomingRecyclerList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<upcomingEventsClass> arrupcomingEvents;
    private List<upcomingEventsClass> arrCopy;
    private upcomingEventsAdapter uadapter;
    public String venueId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_tab4, container, false);

        //Apply Sort dropdown
        final Spinner spinner1 = (Spinner) view.findViewById(R.id.sortById);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource( getActivity().getBaseContext(), R.array.sort_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        //Apply order dropdown
        final Spinner spinner2 = (Spinner) view.findViewById(R.id.orderById);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource( getActivity().getBaseContext(), R.array.order_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //When spinner 1 changes
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String sortby_val = spinner1.getSelectedItem().toString();
                String orderby_val = spinner2.getSelectedItem().toString();
                System.out.println("On 1 changed "+ sortby_val+" "+orderby_val);
                if(sortby_val.equals("Default")){
                    spinner2.setSelection(0);
                    spinner2.setEnabled(false);
                }else{
                    spinner2.setEnabled(true);
                }
                if(arrupcomingEvents!= null){
                    mySortFunc(sortby_val,orderby_val);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String sortby_val = spinner1.getSelectedItem().toString();
                String orderby_val = spinner2.getSelectedItem().toString();
                System.out.println("On 2 changed "+ sortby_val+" "+orderby_val);
                if(arrupcomingEvents!= null){
                    mySortFunc(sortby_val,orderby_val);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        upcomingRecyclerList = view.findViewById(R.id.urecyclerviewId);
        //recyclerView.setVisibility(View.GONE);
     //   Intent intentFromForm = getIntent();
       // Bundle bundleFromForm = intentFromForm.getExtras();

        arrupcomingEvents= new ArrayList<>();
        arrCopy = new ArrayList<>();
        uadapter = new upcomingEventsAdapter(getContext(),arrupcomingEvents);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(upcomingRecyclerList.getContext(), linearLayoutManager.getOrientation());

        upcomingRecyclerList.setHasFixedSize(true);
        upcomingRecyclerList.setLayoutManager(linearLayoutManager);
        upcomingRecyclerList.addItemDecoration(dividerItemDecoration);
        upcomingRecyclerList.setAdapter(uadapter);

        RequestQueue reqqueue = Volley.newRequestQueue(getContext());
//        String venuename = getArguments().getString("venuename");

        String venuename = getArguments().getString("venuename2");
        System.out.println("IN TAB4 DETAILS:");
        System.out.println("Venuename" + venuename);

      //  String venuename = "Staples";
        String songkickUrl1 = "http://homework8-projec-1541555481784.appspot.com/songkick/"+venuename;
        System.out.println(songkickUrl1);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, songkickUrl1,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("INSIDE THE SONGKICK 2 LIST RESPONSE");
                            JSONArray aneventarr = response.getJSONObject("body").getJSONObject("resultsPage").getJSONObject("results").getJSONArray("event");

                            for (int i = 0; i < Math.min(5,aneventarr.length()); i++) {
                                upcomingEventsClass upcomingObj = new upcomingEventsClass();

                                JSONObject ajsonObj = aneventarr.getJSONObject(i);
                                upcomingObj.setEname(ajsonObj.getString("displayName"));
                                upcomingObj.setArtist(ajsonObj.getJSONArray("performance").getJSONObject(0).getString("displayName"));
                                upcomingObj.setEdate(ajsonObj.getJSONObject("start").getString("date"));
                                upcomingObj.setEtime(ajsonObj.getJSONObject("start").getString("time"));
                                upcomingObj.setEtype("Type: " + ajsonObj.getString("type"));
                                upcomingObj.setEurl(ajsonObj.getString("uri"));

                                System.out.println(upcomingObj.getEname());
                                System.out.println(upcomingObj.getArtist());
                                System.out.println(upcomingObj.getEdate());
                                System.out.println(upcomingObj.getEtime());
                                System.out.println(upcomingObj.getEtype());
                                System.out.println(upcomingObj.getEurl());

                                arrupcomingEvents.add(upcomingObj);

//                                System.out.print(arrupcomingEvents);
//                                System.out.print(arrupcomingEvents.size());
                            }

                        } catch (JSONException e) {
                            System.out.println("IN CATCH");
                            e.printStackTrace();
                        }
                        for(int i=0; i<arrupcomingEvents.size(); i++){
                            arrCopy.add(arrupcomingEvents.get(i));
                        }
                        uadapter.notifyDataSetChanged();
                        //    progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        reqqueue.add(stringRequest);
        return  view;
    }

    private void mySortFunc(final String sortby_val, final String orderby_val) {
        System.out.println("SORTED VAL IN INNER FUNC: "+sortby_val);
        System.out.println("ORDER VAL IN INNER FUNC: "+orderby_val);
        Collections.sort(arrupcomingEvents, new Comparator<upcomingEventsClass>() {
            @Override
            public int compare(upcomingEventsClass o1, upcomingEventsClass o2) {
                if(sortby_val.equals("Event Name"))
                    return o1.getEname().compareTo(o2.getEname());
                else if(sortby_val.equals("Artist")){
                    return o1.getArtist().compareTo(o2.getArtist());
                }
                else if(sortby_val.equals("Artist")){
                    return o1.getArtist().compareTo(o2.getArtist());
                }
                else if(sortby_val.equals("Type")){
                    return o1.getEtype().compareTo(o2.getEtype());
                }else if(sortby_val.equals("Time")){
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-dd-MM");
                    try{
                        return format.parse(o1.getEdate()).compareTo(format.parse(o2.getEdate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
                else return 0;
            }
        });
        for(int i=0; i < arrupcomingEvents.size(); i++){
            System.out.println(arrupcomingEvents.get(i).getArtist());
        }

        if(sortby_val.equals("Default")){
            for(int i=0; i<arrupcomingEvents.size(); i++){
                arrupcomingEvents.set(i, arrCopy.get(i));
            }
        }
        //if descending reverse too
        if(orderby_val.equals("Descending")){
            Collections.reverse(arrupcomingEvents);
            System.out.println("In descending order:");
            for(int i=0; i<arrupcomingEvents.size(); i++){
                System.out.println(arrupcomingEvents.get(i).getArtist());
            }
        }
        uadapter.notifyDataSetChanged();
    }

}

