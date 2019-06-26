package com.example.nikitaparmar.eventsearch;

import android.app.assist.AssistStructure;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFragment extends Fragment {
    private static  final  String TAG  = "SearchFragment";
    View view;
    int first_or_second = 0;
    public String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain","Aaa","Abcd", "Akfdknmc"
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, COUNTRIES);
        AutoCompleteTextView textView = view.findViewById(R.id.keywordEditText);
        textView.setAdapter(adapter);

        //Apply Category Spinner
        Spinner spinner1 = (Spinner) view.findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource( getActivity().getBaseContext(), R.array.category_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        //Apply Distance Unit Spinner
        Spinner spinner2 = (Spinner) view.findViewById(R.id.distanceunitSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource( getActivity().getBaseContext(), R.array.distanceunit_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        //Know which Radio is clicked to enabl and disable location text
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            RadioButton whichRadioButton;
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                TextView olocationError = view.findViewById(R.id.location_error);
                olocationError.setVisibility(View.GONE);
                EditText locationtext = (EditText) view.findViewById(R.id.locationText);
                whichRadioButton = view.findViewById(checkedId);
                first_or_second = group.indexOfChild(whichRadioButton);

                // checkedId is the RadioButton selected
                if(first_or_second == 0){
                    locationtext.setEnabled(false);
                    locationtext.setFocusable(false);
                    locationtext.setFocusableInTouchMode(false);
                    locationtext.setText("");

                }else{
                    locationtext.setEnabled(true);
                    locationtext.setFocusable(true);
                    locationtext.setFocusableInTouchMode(true);
                }
            }
        });
        //On click of SEARCH Button
        Button searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AutoCompleteTextView okeyword = view.findViewById(R.id.keywordEditText);
                String keyword = okeyword.getText().toString();
                TextView okeywordError = view.findViewById(R.id.keyword_error);

                Spinner ospinner1 = (Spinner) view.findViewById(R.id.categorySpinner);
                String spinner1 = ospinner1.getSelectedItem().toString();

                EditText odistance = (EditText) view.findViewById(R.id.distanceEditText);
                String distance= odistance.getText().toString();

                Spinner ospinner2 = (Spinner) view.findViewById(R.id.distanceunitSpinner);
                String spinner2 = ospinner2.getSelectedItem().toString();

                EditText olocationtext = (EditText) view.findViewById(R.id.locationText);
                String locationtext = olocationtext.getText().toString();

                TextView olocationError = view.findViewById(R.id.location_error);

//               String s = "K:"+ keyword+ " C: "+spinner1 +" d: "+distance+" du: "+spinner2+ " lt: "+ locationtext;
//               Toast toast1 = Toast.makeText(getContext(), s , Toast.LENGTH_LONG);
//               toast1.show();

                int anyError = 0;
                if(keyword.equals("")){
                    anyError = 1;
                    okeywordError.setVisibility(View.VISIBLE);
                }else{
                    okeywordError.setVisibility(View.GONE);
                }

                if(locationtext.equals("") && first_or_second == 1){
                    olocationError.setVisibility(View.VISIBLE);
                    anyError = 1;
                }else{
                    olocationError.setVisibility(View.GONE);
                }

                if(anyError == 1){
                    Toast toast1 = Toast.makeText(getContext(), "Please fix all fields with errors" , Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    Intent intentToEventList = new Intent(getContext(), eventListActivity.class);
                    Bundle bundle_for_eventlist = new Bundle();

                    String segmentId;
                    bundle_for_eventlist.putString("category", spinner1);
                    if(spinner1.equals("All")){
                        segmentId = "All";
                    }else if(spinner1.equals("Music")) {
                        segmentId = "KZFzniwnSyZfZ7v7nJ";
                    }else if(spinner1.equals("Sports")) {
                        segmentId = "KZFzniwnSyZfZ7v7nE";
                    }else if(spinner1.equals("Film")) {
                        segmentId = "KZFzniwnSyZfZ7v7nn";
                    }else if(spinner1.equals("Miscellaneous")) {
                        segmentId = "KZFzniwnSyZfZ7v7n1";
                    }else{
                        segmentId = "KZFzniwnSyZfZ7v7na";
                    }

                    String dist;
                    if(distance.equals("")){
                        dist = "10";
                    }else{
                        dist = distance;
                    }
                    String distUnit;
                    if(spinner2.equals("Miles")){
                        distUnit = spinner2.toLowerCase();
                    }else{
                        distUnit = "km";
                    }

                    bundle_for_eventlist.putString("keyword", keyword);
                    bundle_for_eventlist.putString("segmentId", segmentId);
                    bundle_for_eventlist.putString("dist", dist);
                    bundle_for_eventlist.putString("distUnit", distUnit);
                    if(locationtext.equals("")){
                        bundle_for_eventlist.putString("locationtext", "Nolocation");
                    }else{
                        bundle_for_eventlist.putString("locationtext", locationtext);
                    }
                    bundle_for_eventlist.putString("latitude", "34.0324");
                    bundle_for_eventlist.putString("longitude", "-118.5584");

                    intentToEventList.putExtras(bundle_for_eventlist);
                    startActivity(intentToEventList);
                }
            }
        });

        //On click of CLEAR Button
        Button clearButton = view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AutoCompleteTextView okeyword = view.findViewById(R.id.keywordEditText);
                okeyword.setText("");

                TextView okeywordError = view.findViewById(R.id.keyword_error);
                okeywordError.setVisibility(View.GONE);

                Spinner ospinner1 = view.findViewById(R.id.categorySpinner);
                ospinner1.setSelection(0);

                EditText odistance = view.findViewById(R.id.distanceEditText);
                odistance.setText("");

                Spinner ospinner2 = view.findViewById(R.id.distanceunitSpinner);
                ospinner2.setSelection(0);

                RadioButton radio1 = view.findViewById(R.id.radioButton1);
                radio1.setChecked(true);

                RadioButton radio2 = view.findViewById(R.id.radioButton2);
                radio2.setChecked(false);

                EditText olocationtext = view.findViewById(R.id.locationText);
                olocationtext.setText("");
                olocationtext.setEnabled(false);
                olocationtext.setFocusable(false);
                olocationtext.setFocusableInTouchMode(false);

                TextView olocationError = view.findViewById(R.id.location_error);
                olocationError.setVisibility(View.GONE);
            }
        });

        return  view;
    }




}
