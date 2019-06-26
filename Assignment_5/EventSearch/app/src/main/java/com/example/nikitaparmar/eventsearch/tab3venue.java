package com.example.nikitaparmar.eventsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Line;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Struct;

public class tab3venue extends Fragment {

    private TextView name;
    private TextView address;
    private TextView city;
    private TextView phone;
    private TextView hours;
    private TextView general;
    private TextView child;

    private LinearLayout nameLL;
    private LinearLayout addressLL;
    private LinearLayout cityLL;
    private LinearLayout phoneLL;
    private LinearLayout hoursLL;
    private LinearLayout generalLL;
    private LinearLayout childLL;


    MapView mMapView;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venue_tab3, container, false);

        name= view.findViewById(R.id.dataName);
        address = view.findViewById(R.id.dataAddress);
        city = view.findViewById(R.id.dataCity);
        phone = view.findViewById(R.id.dataPhone);
        hours = view.findViewById(R.id.dataHours);
        general = view.findViewById(R.id.dataGeneral);
        child = view.findViewById(R.id.dataChild);

        nameLL = view.findViewById(R.id.nameLLId);
        addressLL = view.findViewById(R.id.addressLLId);
        cityLL = view.findViewById(R.id.cityLLId);
        phoneLL = view.findViewById(R.id.cityLLId);
        hoursLL = view.findViewById(R.id.hoursLLId);
        generalLL = view.findViewById(R.id.generalLLId);
        childLL = view.findViewById(R.id.childLLId);

        String venuename = getArguments().getString("venuename2");
        System.out.println("IN TAB3 DETAILS:");
        System.out.println("Venuename" + venuename);

        final String[] latitude = {"44"};
        final String[] longitude = {"-110"};
        RequestQueue reqqueue = Volley.newRequestQueue(getContext());
        String venueDetailsUrl = "http://homework8-projec-1541555481784.appspot.com/venuedetail/"+venuename;
        System.out.println(venueDetailsUrl);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, venueDetailsUrl,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            try{
                                String venuename = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name");
                                //     System.out.println("Venue name: "+ venuename);
                                name.setText(venuename);
                            }catch (Exception e){
                                nameLL.setVisibility(View.GONE);
                            }

                            try{
                                String address1 = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("address").getString("line1");
                                // System.out.println("Address: "+address1);
                                address.setText(address1);
                            }catch (Exception e){
                                addressLL.setVisibility(View.GONE);
                            }
                            try{
                                String scity = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("city").getString("name");
                                //  System.out.println("City: "+scity);

                                String sstate = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("state").getString("name");
                                //  System.out.println("State: "+sstate);
                                city.setText(scity+", "+sstate);
                            }catch (Exception e){
                                cityLL.setVisibility(View.GONE);
                            }
                            try{
                                String sphone = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("boxOfficeInfo").getString("phoneNumberDetail");
                                //           System.out.println("Phone: "+phone);
                                phone.setText(sphone);
                            }catch (Exception e){
                                phoneLL.setVisibility(View.GONE);
                            }
                            try{
                                String openhours = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("boxOfficeInfo").getString("openHoursDetail");
                                //         System.out.println("Openhours: "+openhours);
                                hours.setText(openhours);
                            }catch (Exception e){
                                hoursLL.setVisibility(View.GONE);
                            }
                            try{
                                String sgeneral = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("generalInfo").getString("generalRule");
                                //       System.out.println("General: "+sgeneral);
                                general.setText(sgeneral);
                            }catch (Exception e){
                                generalLL.setVisibility(View.GONE);
                            }
                            try{
                                String schild = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("generalInfo").getString("generalRule");
                                //     System.out.println("Child: "+schild);
                                child.setText(schild);
                            }catch (Exception e){
                                childLL.setVisibility(View.GONE);
                            }

                            latitude[0] = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("location").getString("latitude");
                            longitude[0] = response.getJSONObject("body").getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("location").getString("longitude");
                            System.out.println(latitude[0] +" LAT LON "+ longitude[0]);
                            makeMap(latitude[0],longitude[0]);

                        } catch (JSONException e) {
                            e.printStackTrace();
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
            mMapView = view.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            reqqueue.add(stringRequest);

        return  view;
    }

    private void makeMap(final String lat, final String lon) {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
//                System.out.println("Venue lat lon: "+Float.parseFloat(latitude[0]));
                LatLng venuelocation= new LatLng(Float.parseFloat(lat), Float.parseFloat(lon));
                googleMap.addMarker(new MarkerOptions().position(venuelocation).title("Venue Location").snippet("1"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(venuelocation).zoom(15).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
