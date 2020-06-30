package com.pccw.hikerph.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHikeAdapter extends RecyclerView.Adapter<MyHikeAdapter.MyHikeViewHolder> {

    private static final String TAG = "MyHikeAdapter";
    private OnMyHikeEventClickListener onMyHikeEventClickListener;

    private List<HikeDto> hikeList;

    public MyHikeAdapter(List<HikeDto> hikeList, OnMyHikeEventClickListener onMyHikeEventClickListener) {
        this.hikeList = hikeList;
        this.onMyHikeEventClickListener = onMyHikeEventClickListener;
    }


    @NonNull
    @Override
    public MyHikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_card_fragment_my_hike,parent,false);
        MyHikeViewHolder myHikeViewHolder = new MyHikeViewHolder(view,onMyHikeEventClickListener);
        return myHikeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHikeViewHolder holder, int position) {

        HikeDto hikeDto = hikeList.get(position);

        holder.eventName.setText(hikeDto.getEventName());
        holder.mtName.setText(hikeDto.getMtName());
        holder.mtLocation.setText(hikeDto.getLocation());
        holder.date.setText(hikeDto.getStartDate());

        System.out.println("Profile Banner - "+ hikeDto.getEventName()+" : "+hikeDto.getPath_banner());
    }

    @Override
    public int getItemCount() {

        int count = hikeList.size();
        Log.d(TAG, "getItemCount: No of HikeDto "+ count);
        return count;
    }

    public static class MyHikeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView eventName;
        TextView mtName;
        TextView mtLocation;
        TextView date;
        OnMyHikeEventClickListener onMyHikeEventClickListener;

        public MyHikeViewHolder(@NonNull View itemView, OnMyHikeEventClickListener onMyHikeEventClickListener) {

            super(itemView);
            eventName = itemView.findViewById(R.id.eventName_my_hike);
            mtName = itemView.findViewById(R.id.mtName_my_hike);
            mtLocation = itemView.findViewById(R.id.location_my_hike);
            date = itemView.findViewById(R.id.date_my_hike);

            this.onMyHikeEventClickListener = onMyHikeEventClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onMyHikeEventClickListener.onMyHikeClickListener(getAdapterPosition());
        }
    }

    public interface OnMyHikeEventClickListener{
        void onMyHikeClickListener(int index_hike);
    }


}
