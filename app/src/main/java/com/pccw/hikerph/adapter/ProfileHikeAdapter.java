package com.pccw.hikerph.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileHikeAdapter extends RecyclerView.Adapter<ProfileHikeAdapter.ProfileHikeViewHolder> {

    List<HikeDto> upcomingHikeLs;

    Context context;

    private OnProfileHikeEventClickListener onProfileHikeEventClickListener;

    public interface OnProfileHikeEventClickListener{
        void onProfileHikeEventClickListener(int position);
    }


    public ProfileHikeAdapter(Context context, List<HikeDto> upcomingHikeLs, OnProfileHikeEventClickListener onProfileHikeEventClickListener) {
        this.upcomingHikeLs = upcomingHikeLs;
        this.onProfileHikeEventClickListener = onProfileHikeEventClickListener;
        this.context = context;
    }


    @NonNull
    @Override
    public ProfileHikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_hike,parent,false);
        ProfileHikeViewHolder profileHikeViewHolder = new ProfileHikeViewHolder(view, onProfileHikeEventClickListener);
        return profileHikeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileHikeViewHolder holder, int position) {

        HikeDto hike = upcomingHikeLs.get(position);
        holder.mtName.setText(hike.getMtName());
        holder.mtLocation.setText(hike.getLocation());
        holder.difficultyLvl.setText(hike.getElevation() + " (masl)");
        holder.startDate.setText(hike.getStartDate());

        Glide.with(context)
                .load(hike.getImage_banner())
                .placeholder(R.drawable.mt_icon)
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return upcomingHikeLs.size();
    }

    public static class ProfileHikeViewHolder extends RecyclerView.ViewHolder {
        TextView mtName;
        TextView mtLocation;
        TextView difficultyLvl;
        TextView startDate;
        ImageView imageView;

        OnProfileHikeEventClickListener onProfileHikeEventClickListener;

        public ProfileHikeViewHolder(@NonNull View itemView, OnProfileHikeEventClickListener onClicklistener) {
            super(itemView);
            mtName = itemView.findViewById(R.id.mtName);
            mtLocation = itemView.findViewById(R.id.mtLocation);
            difficultyLvl = itemView.findViewById(R.id.difficultyLvl);
            startDate = itemView.findViewById(R.id.startDate);
            imageView = itemView.findViewById(R.id.imageView);
            this.onProfileHikeEventClickListener = onClicklistener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onProfileHikeEventClickListener.onProfileHikeEventClickListener(getAdapterPosition());
                }
            });
        }
    }
}
