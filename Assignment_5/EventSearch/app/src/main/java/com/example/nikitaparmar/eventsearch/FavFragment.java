package com.example.nikitaparmar.eventsearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavFragment extends Fragment {
   private static  final  String TAG  = "FavFragment";
    private RecyclerView favList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<EventListClass> arreventList;
    private favListAdapter fadapter;
    private RecyclerView recyclerView;
    private LinearLayout progressBar;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fav_fragment, container, false);

        favList = view.findViewById(R.id.feventListRV);
        SharedPreferences varsharedPref;
        String filenameSharedPref = "SharedPreferenceFile";
        varsharedPref = getContext().getSharedPreferences(filenameSharedPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorSharedPref = varsharedPref.edit();
//        editorSharedPref.clear();
//        editorSharedPref.commit();
        Map<String, ?> allEntries = varsharedPref.getAll();
        System.out.println("All entry in favfragment"+allEntries);
        fadapter = new favListAdapter(getContext(), allEntries);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(favList.getContext(), linearLayoutManager.getOrientation());
        favList.setHasFixedSize(true);
        favList.setLayoutManager(linearLayoutManager);
        favList.addItemDecoration(dividerItemDecoration);
        favList.setAdapter(fadapter);

        fadapter.notifyDataSetChanged();

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favList = view.findViewById(R.id.feventListRV);
        SharedPreferences varsharedPref;
        String filenameSharedPref = "SharedPreferenceFile";
        varsharedPref = getContext().getSharedPreferences(filenameSharedPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorSharedPref = varsharedPref.edit();

        Map<String, ?> allEntries = varsharedPref.getAll();
        System.out.println("Size in onResume: "+allEntries.size());
        System.out.println("All entry in favfragment"+allEntries);

        fadapter = new favListAdapter(getContext(), allEntries);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(favList.getContext(), linearLayoutManager.getOrientation());
        favList.setHasFixedSize(true);
        favList.setLayoutManager(linearLayoutManager);
        favList.addItemDecoration(dividerItemDecoration);
        favList.setAdapter(fadapter);

        fadapter.notifyDataSetChanged();

    }
}


