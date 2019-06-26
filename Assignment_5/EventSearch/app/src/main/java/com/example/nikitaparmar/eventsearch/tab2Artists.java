package com.example.nikitaparmar.eventsearch;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class tab2Artists extends Fragment {
    private RecyclerView artistRecyclerList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ArtistsClass> arrArtist;
    private artistAdapter aadapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_tab2, container, false);

        artistRecyclerList = view.findViewById(R.id.recyclerArtistId);

        arrArtist= new ArrayList<>();
        aadapter = new artistAdapter(getContext(),arrArtist);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(artistRecyclerList.getContext(), linearLayoutManager.getOrientation());

        artistRecyclerList.setHasFixedSize(true);
        artistRecyclerList.setLayoutManager(linearLayoutManager);
        artistRecyclerList.addItemDecoration(dividerItemDecoration);
        artistRecyclerList.setAdapter(aadapter);
        String artistname1 = getArguments().getString("artistname1");
        String artistname2 = getArguments().getString("artistname2");
        String category = getArguments().getString("category");

        System.out.println("IN TAB2 DETAILS:");
        System.out.println("artistname1" + artistname1);
        System.out.println("artistname2" + artistname2);
        int len=1;
        if(artistname1 != null && artistname2 == null){
            len = 1;
        }else if(artistname1 == null && artistname2 == null){
            len = 0;
        }else if(artistname1 != null && artistname2 != null){
            len = 2;
        }
        for (int i =0; i<len; i++){
            final ArtistsClass artistObj = new ArtistsClass();

            if(category.equals("Music")){
                RequestQueue reqqueue1 = Volley.newRequestQueue(getContext());
                String spotifyUrl;
                if(len == 0){
                    spotifyUrl = "http://homework8-projec-1541555481784.appspot.com/spotify/"+ artistname1;
                }else{
                    spotifyUrl = "http://homework8-projec-1541555481784.appspot.com/spotify/"+ artistname2;
                }
                System.out.println(spotifyUrl);
                JsonObjectRequest stringRequest1 = new JsonObjectRequest(Request.Method.GET, spotifyUrl,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println("INSIDE Spotify RESPONSE");
                                    String sname = response.getJSONObject("message").getJSONObject("artists").getJSONArray("items").getJSONObject(0).getString("name");
                                    artistObj.setSname(sname);
                                    String sfollowers = response.getJSONObject("message").getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("followers").getString("total");
                                    artistObj.setSfollowers(sfollowers);
                                    String spopularity = response.getJSONObject("message").getJSONObject("artists").getJSONArray("items").getJSONObject(0).getString("popularity");
                                    artistObj.setSpopularity(spopularity);
                                    String scheckout = response.getJSONObject("message").getJSONObject("artists").getJSONArray("items").getJSONObject(0).getJSONObject("external_urls").getString("spotify");
                                    artistObj.setScheckat(scheckout);


                                } catch (JSONException e) {
                                    System.out.println("IN CATCH");
                                    e.printStackTrace();
                                }

                                aadapter.notifyDataSetChanged();
                                //    progressDialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  mTextView.setText("That didn't work!");
                    }
                });
                // Add the request to the RequestQueue.
                reqqueue1.add(stringRequest1);
            }

            RequestQueue reqqueue = Volley.newRequestQueue(getContext());
            String gcustomUrl;
            if(len == 0){
                gcustomUrl = "http://homework8-projec-1541555481784.appspot.com/gcustomsearch/"+ artistname1;
                artistObj.setArtistHeader(artistname1);
            }else{
                gcustomUrl = "http://homework8-projec-1541555481784.appspot.com/gcustomsearch/"+ artistname2;
                artistObj.setArtistHeader(artistname2);
            }
            System.out.println(gcustomUrl);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, gcustomUrl,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                System.out.println("INSIDE GCUSTOM RESPONSE");

                                String img1 = response.getJSONObject("body").getJSONArray("items").getJSONObject(0).getString("link");
                                artistObj.setGimg1(img1);
                                System.out.println("Img1: " + img1);

                                String img2 = response.getJSONObject("body").getJSONArray("items").getJSONObject(1).getString("link");
                                artistObj.setGimg2(img2);
                                System.out.println("Img2: " + img2);

                                String img3 = response.getJSONObject("body").getJSONArray("items").getJSONObject(2).getString("link");
                                artistObj.setGimg3(img3);
                                System.out.println("Img3: " + img3);

                                String img4 = response.getJSONObject("body").getJSONArray("items").getJSONObject(3).getString("link");
                                artistObj.setGimg4(img4);
                                System.out.println("Img4: " + img4);

                                String img5 = response.getJSONObject("body").getJSONArray("items").getJSONObject(4).getString("link");
                                artistObj.setGimg5(img5);
                                System.out.println("Img5: " + img5);

                                String img6 = response.getJSONObject("body").getJSONArray("items").getJSONObject(5).getString("link");
                                artistObj.setGimg6(img6);
                                System.out.println("Img6: " + img6);

                                String img7 = response.getJSONObject("body").getJSONArray("items").getJSONObject(6).getString("link");
                                artistObj.setGimg7(img7);
                                System.out.println("Img7: " + img7);

                                String img8 = response.getJSONObject("body").getJSONArray("items").getJSONObject(7).getString("link");
                                artistObj.setGimg8(img8);
                                System.out.println("Img8: " + img8);

                                arrArtist.add(artistObj);
                                System.out.println("Artist OBJ: " + artistObj);

                            } catch (JSONException e) {
                                System.out.println("IN CATCH");
                                e.printStackTrace();
                            }

                            aadapter.notifyDataSetChanged();
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
        }
        return  view;
    }
}
