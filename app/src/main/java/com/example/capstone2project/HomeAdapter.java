package com.example.capstone2project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstone2project.databinding.ListHomeBinding;

public class HomeAdapter extends ListAdapter<HomeData, HomeAdapter.HomeViewHolder> {

    private static OnItemClickListener listener;

    public HomeAdapter() {
        super(diffCallback);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home, parent, false);
        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static void setOnClickListener(OnItemClickListener listener) {
        HomeAdapter.listener = listener;
    }

    public interface OnItemClickListener {
        void onClicked(HomeData data);
    }

    static class HomeViewHolder extends RecyclerView.ViewHolder {
        private final ListHomeBinding binding;

        public HomeViewHolder(View view) {
            super(view);
            binding = ListHomeBinding.bind(view);
        }

        public void bind(HomeData data) {
            binding.namaworkout.setText(data.getNamaWorkout());
            binding.detailcard.setText(data.getDetail());
            binding.jumlah.setText(data.getJumlah());

            Glide.with(itemView.getContext())
                    .load(data.getImg())
                    .into(binding.imgWorkout);

            binding.card.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onClicked(data);
                }
            });
        }
    }

    private static final DiffUtil.ItemCallback<HomeData> diffCallback = new DiffUtil.ItemCallback<HomeData>() {
        @Override
        public boolean areItemsTheSame(HomeData oldItem, HomeData newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(HomeData oldItem, HomeData newItem) {
            return oldItem.equals(newItem);
        }
    };
}
