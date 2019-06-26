package com.example.nikitaparmar.eventsearch;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class eventListActivity extends AppCompatActivity {

    private RecyclerView eventList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<EventListClass> arreventList;
    private eventListAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout progressBar;

    public void onClick(View view , int position){
        // The onClick implementation of the RecyclerView item click
        final EventListClass eventlistObj = arreventList.get(position);
   //     Intent i = new Intent(this, eventDetailFourTabs.class);

     //   startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        progressBar = (LinearLayout)findViewById(R.id.progressLayoutId);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.eventListRV);
        recyclerView.setVisibility(View.GONE);
    //    getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentFromForm = getIntent();
        Bundle bundleFromForm = intentFromForm.getExtras();

        eventList = findViewById(R.id.eventListRV);

        arreventList = new ArrayList<>();
        adapter = new eventListAdapter(getApplicationContext(),arreventList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(eventList.getContext(), linearLayoutManager.getOrientation());

        eventList.setHasFixedSize(true);
        eventList.setLayoutManager(linearLayoutManager);
        eventList.addItemDecoration(dividerItemDecoration);
        eventList.setAdapter(adapter);
//        adapter.setClickListener(this);

        if(!bundleFromForm.isEmpty()){
            String keyword =  bundleFromForm.getString("keyword");
            String segmentId =  bundleFromForm.getString("segmentId");
            String distance =  bundleFromForm.getString("dist");
            String distaceunit =  bundleFromForm.getString("distUnit");
            String locationtext =  bundleFromForm.getString("locationtext");
            String latitude = bundleFromForm.getString("latitude");
            String longitude = bundleFromForm.getString("longitude");

//            System.out.println(keyword);
//            System.out.println(segmentId);
//            System.out.println(distance);
//            System.out.println(distaceunit);
//            System.out.println(locationtext);

            RequestQueue reqqueue = Volley.newRequestQueue(this);
            String eventListUrl = "http://homework8-projec-1541555481784.appspot.com/eventlist/"+ keyword +"/"+ segmentId+"/"+ distance +"/"+
                    distaceunit +"/"+ latitude+"/"+longitude+"/"+locationtext;
            System.out.println(eventListUrl);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, eventListUrl, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            ImageView categoryImageView;
                            String category;
                            int id;
                            try {
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                System.out.println("INSIDE THE EVENT LIST RESPONSE");
                               JSONArray aneventarr = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("events");
                                for (int i = 0; i < aneventarr.length(); i++) {
                                    JSONObject ajsonObj = aneventarr.getJSONObject(i);
                                    EventListClass eventObj = new EventListClass();
                                    try{
                                        eventObj.setEventName(ajsonObj.getString("name"));
                                    }catch (Exception e){
                                        eventObj.setEventName("N/A");
                                    }
                                    try{
                                        eventObj.setCategory(ajsonObj.getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name"));
                                    }catch (Exception e){
                                        eventObj.setCategory("N/A");
                                    }
                                    try{
                                        eventObj.setDateTime(ajsonObj.getJSONObject("dates").getJSONObject("start").getString("localDate"),
                                                ajsonObj.getJSONObject("dates").getJSONObject("start").getString("localTime"));
                                    }catch (Exception e){
                                        eventObj.setDateTime("N/A","N/A");
                                    }
                                    try{
                                        eventObj.setVenueName(ajsonObj.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name"));
                                    }catch (Exception e){
                                        eventObj.setVenueName("N/A");
                                    }
                                    try{
                                        eventObj.setEid(ajsonObj.getString("id"));
                                    }catch (Exception e){
                                        eventObj.setEid("N/A");
                                    }

                                    System.out.print(eventObj.getEid());
                                    System.out.print(eventObj.getEventName());
                                    System.out.print(eventObj.getVenueName());
                                    System.out.print(eventObj.getDateTime());
                                    System.out.print(eventObj.getCategory());

                                    arreventList.add(eventObj);

                                }
                            } catch (JSONException e) {
                                System.out.println("IN CATCH");
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
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
        }else{

        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
