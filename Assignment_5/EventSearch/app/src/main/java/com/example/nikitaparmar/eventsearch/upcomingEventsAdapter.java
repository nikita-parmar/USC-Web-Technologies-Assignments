package com.example.nikitaparmar.eventsearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class upcomingEventsAdapter  extends RecyclerView.Adapter<upcomingEventsAdapter.ViewHolder> {
    private Context context;
    private List<upcomingEventsClass> list;

    public upcomingEventsAdapter(Context ucontext, List<upcomingEventsClass> ulist) {
        context = ucontext;
        list = ulist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.an_upcoming_card, viewGroup, false);
        return new upcomingEventsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull upcomingEventsAdapter.ViewHolder viewHolder, int i) {
        upcomingEventsClass upcomingEventsObj = list.get(i);

        viewHolder.ueventName.setText(upcomingEventsObj.getEname());
        viewHolder.uartisName.setText(upcomingEventsObj.getArtist());
        viewHolder.udateTime.setText(upcomingEventsObj.getEdate() + " " + upcomingEventsObj.getEtime());
        viewHolder.utype.setText(upcomingEventsObj.getEtype());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView ueventName;
        public TextView uartisName;
        public TextView udateTime;
        public TextView utype;
        public CardView card_layout;
        LinearLayout acard;
        public ViewHolder(@NonNull View view) {
            super(view);

            ueventName = view.findViewById(R.id.ueventName);
            uartisName = view.findViewById(R.id.uartisName);
            udateTime = view.findViewById(R.id.udateTime);
            utype = view.findViewById(R.id.utype);

            acard = view.findViewById(R.id.acard);
            acard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            upcomingEventsClass upcomingEventsObj= list.get(getAdapterPosition());

            Uri uri = Uri.parse(upcomingEventsObj.getEurl()); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }
}
