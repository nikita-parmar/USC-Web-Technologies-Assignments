package com.example.nikitaparmar.eventsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class tab1events extends Fragment {
//    private static  final  String TAG  = "";
    private TextView artist;
    private TextView venue;
    private TextView dateTime;
    private TextView categories;
    private TextView price;
    private TextView tstatus;
    private TextView buyat;
    private TextView seatmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_tab1, container, false);

        String eventId = getArguments().getString("eventId");

        System.out.println("IN TAB1 DETAILS:");
        System.out.println("Event ID" + eventId);

        artist = view.findViewById(R.id.dataArtist);
        venue = view.findViewById(R.id.dataVenue);
        dateTime = view.findViewById(R.id.dataTime);
        categories= view.findViewById(R.id.dataCategory);
        price = view.findViewById(R.id.dataPrice);
        tstatus = view.findViewById(R.id.dataStatus);
        buyat = view.findViewById(R.id.dataBuy);
        seatmap = view.findViewById(R.id.dataSeatmap);

//       Intent intent = getIntent();
//       Bundle bundleFromForm = intentFromForm.getExtras();
//       String keyword =  bundleFromForm.getString("keyword");

//        tab3venue fragment = new tab3venue();
//        Bundle bundle = new Bundle();
//        bundle.putString("username","Nikita");
//        fragment.setArguments(bundle);
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.second_frag,fragment).commit();

        RequestQueue reqqueue = Volley.newRequestQueue(getContext());

        String eventDetailsUrl = "http://homework8-projec-1541555481784.appspot.com/eventdetail/"+eventId;
        System.out.println(eventDetailsUrl);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, eventDetailsUrl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //                          progressBar.setVisibility(View.GONE);
                        System.out.println("Artists name: ");
                        try {
                            try{
                                JSONArray artists = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("attractions");
                                String allartist = "";
                                for (int i = 0; i < artists.length(); i++) {
                                    try{
                                        allartist += artists.getJSONObject(i).getString("name");
                                    }catch (Exception e){

                                    }
                                    if(i< artists.length()-1){
                                        allartist += " | ";
                                    }
                                }
                                artist.setText(allartist);
                            }catch (Exception e){
                                artist.setText("N/A");
                            }
                            try{
                                String venuename = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name");
                                System.out.println("Venue name: "+ venuename);
                                venue.setText(venuename);
                            }catch (Exception e){
                                venue.setText("N/A");
                            }
                            try{
                                String date = response.getJSONObject("body").getJSONObject("dates").getJSONObject("start").getString("localDate");
                                String time = "";
                                try{
                                    time = response.getJSONObject("body").getJSONObject("dates").getJSONObject("start").getString("localTime");
                                }catch (Exception e){
                                    time = "";
                                }
                                System.out.println("Date: "+date+"   Time: "+time);
                                dateTime.setText(date+" "+time);
                            }catch (Exception e){
                                dateTime.setText("N/A");
                            }

                            try {
                                String segment = "";
                                String genre = "";
                                try{
                                    segment = response.getJSONObject("body").getJSONArray("classifications").getJSONObject(0).getJSONObject("segment").getString("name");
                                }catch (Exception e){

                                }
                                try{
                                    genre = response.getJSONObject("body").getJSONArray("classifications").getJSONObject(0).getJSONObject("genre").getString("name");
                                }catch (Exception e){

                                }
                                System.out.println("Segment: " + segment+ " Genre: "+ genre);
                                categories.setText(genre+" "+segment);

                            }catch (Exception e){
                                categories.setText("N/A");
                            }

                            try {
                                String pricemin = "";
                                String pricemax = "";
                                if(response.has("body") &&
                                        response.getJSONArray("priceRanges").length()>0 &&
                                        response.getJSONArray("priceRanges").getJSONObject(0).has("min") &&
                                        response.getJSONArray("priceRanges").getJSONObject(0).has("max")
                                        ){
                                    pricemin = response.getJSONObject("body").getJSONArray("priceRanges").getJSONObject(0).getString("min");
                                    pricemax = response.getJSONObject("body").getJSONArray("priceRanges").getJSONObject(0).getString("max");
                                    price.setText(pricemin+" ~ " + pricemax);
                                }
                                else if(response.has("body") &&
                                        response.getJSONArray("priceRanges").length()>0 &&
                                        response.getJSONArray("priceRanges").getJSONObject(0).has("min") &&
                                        !response.getJSONArray("priceRanges").getJSONObject(0).has("max")
                                        ){
                                    pricemin = response.getJSONObject("body").getJSONArray("priceRanges").getJSONObject(0).getString("min");
                                    price.setText(pricemin);
                                }
                                else if(response.has("body") &&
                                        response.getJSONArray("priceRanges").length()>0 &&
                                        !response.getJSONArray("priceRanges").getJSONObject(0).has("min") &&
                                        response.getJSONArray("priceRanges").getJSONObject(0).has("max")
                                        ){
                                    pricemax = response.getJSONObject("body").getJSONArray("priceRanges").getJSONObject(0).getString("max");
                                    price.setText(pricemax);
                                }
                                else {
                                    price.setText("N/A");
                                }

                            }catch (Exception e){
                                price.setText("N/A");
                            }

                            try {
                                String status = response.getJSONObject("body").getJSONObject("dates").getJSONObject("status").getString("code");
                                tstatus.setText(status);
                            }catch (Exception e){

                            }
                            try {
                                String url = response.getJSONObject("body").getString("url");
                                String atagText = "<a href="+ url +">TicketMaster</a>";
                                buyat.setText(Html.fromHtml(atagText));
                                buyat.setMovementMethod(LinkMovementMethod.getInstance());
                            }catch (Exception e){
                                buyat.setText("N/A");
                            }
                            try {
                                String seatmap1 = response.getJSONObject("body").getJSONObject("seatmap").getString("staticUrl");
                                String atagText = "<a href="+ seatmap1 +">View Here</a>";
                                seatmap.setText(Html.fromHtml(atagText));
                                seatmap.setMovementMethod(LinkMovementMethod.getInstance());
                            }catch (Exception e){
                                seatmap.setText("N/A");
                            }
                        }catch (Exception e){

                        }

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
}
