package com.example.capstone2project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private ArrayList<ScheduleItem> scheduleList;
    private OnItemClickListener mlistener;

    public ScheduleAdapter(ArrayList<ScheduleItem> scheduleItems, OnItemClickListener listener) {
        this.scheduleList = new ArrayList<>(scheduleItems);
        this.mlistener = listener;
    }

    public ScheduleAdapter(ArrayList<ScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }




    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleItem scheduleItem = scheduleList.get(position);
        holder.bind(scheduleItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && mlistener != null) {
                    mlistener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvJudul, tvTanggal, tvWaktu;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvWaktu = itemView.findViewById(R.id.tvWaktu);
        }

        public void bind(ScheduleItem scheduleItem) {
            tvJudul.setText(scheduleItem.getJudul());
            tvTanggal.setText(scheduleItem.getTanggal());
            tvWaktu.setText(scheduleItem.getWaktu());
        }
    }

    public void updateData(ArrayList<ScheduleItem> newList) {
        scheduleList = newList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ScheduleItem getItem(int position) {
        if (position >= 0 && position < scheduleList.size()) {
            return scheduleList.get(position);
        }
        return null;
    }
}
