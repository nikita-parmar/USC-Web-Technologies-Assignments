package com.example.nikitaparmar.eventsearch;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class eventDetails4 extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ViewPager mViewPager;
    private SectionsPageAdapter msectionsPageAdapter;
    public String eventId;
    public String venuename;
    public String venuename2;
    public String artistname1;
    public String artistname2;
    public  String category;
    public SectionsPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details4);

        msectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_info_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_artist);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tab_venue);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_tab_upcoming);
    }

    private void setupViewPager(ViewPager viewPager) {
        Intent i = getIntent();
        eventId = getIntent().getStringExtra("eventId");
        venuename = getIntent().getStringExtra("venuename");
        venuename2 = getIntent().getStringExtra("venuename2");
        artistname1 = getIntent().getStringExtra("artistname1");
        artistname2 = getIntent().getStringExtra("artistname2");
        category = getIntent().getStringExtra("category");
        adapter = new SectionsPageAdapter(getSupportFragmentManager());

        Bundle bundle1 = new Bundle();
        bundle1.putString("eventId", eventId);
        tab1events tab1 = new tab1events();
        tab1.setArguments(bundle1);
        adapter.addFragment(tab1, "EVENTS");

        Bundle bundle2 = new Bundle();
        bundle2.putString("artistname1", artistname1);
        bundle2.putString("artistname2", artistname2);
        bundle2.putString("category", category);
        tab2Artists tab2 = new tab2Artists();
        tab2.setArguments(bundle2);
        adapter.addFragment(tab2, "ARTIST(S)");

        Bundle bundle3 = new Bundle();
        bundle3.putString("venuename", venuename);
        bundle3.putString("venuename2", venuename2);
        tab3venue tab3 = new tab3venue();
        tab3.setArguments(bundle3);
        adapter.addFragment(tab3, "VENUE");

        Bundle bundle4 = new Bundle();
        bundle4.putString("venuename", venuename);
        bundle4.putString("venuename2", venuename2);
        tab4Upcoming tab4 = new tab4Upcoming();
        tab4.setArguments(bundle4);
        adapter.addFragment(tab4, "UPCOMING");

        viewPager.setAdapter(adapter);
    }
}

