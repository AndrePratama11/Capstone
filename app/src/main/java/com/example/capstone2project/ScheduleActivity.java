package com.example.capstone2project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2project.databinding.ScheduleBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {
    private ScheduleBinding binding;
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;
    private ArrayList<ScheduleItem> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ScheduleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();

        scheduleAdapter = new ScheduleAdapter(arrayList, new ScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                editSchedule(position);
            }
        });

        recyclerView.setAdapter(scheduleAdapter);

        binding.newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat Intent untuk membuka ActivityHomePage
                Intent intent = new Intent(ScheduleActivity.this, HomeActivity.class);

                // Memulai aktivitas tanpa animasi transisi
                startActivity(intent);

                // Memberikan kesan pindah halaman instan tanpa animasi tambahan
                overridePendingTransition(0, 0);
            }
        });

        binding.foodcalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat Intent untuk membuka ActivityHomePage
                Intent intent = new Intent(ScheduleActivity.this, FoodActivity.class);

                // Memulai aktivitas tanpa animasi transisi
                startActivity(intent);

                // Memberikan kesan pindah halaman instan tanpa animasi tambahan
                overridePendingTransition(0, 0);
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("schedules");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ScheduleItem scheduleItem = dataSnapshot.getValue(ScheduleItem.class);
                    arrayList.add(scheduleItem);
                }

                scheduleAdapter.updateData(arrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.btnAddSchedule);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddScheduleActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton2 = findViewById(R.id.btnHistory);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                moveToHistory(arrayList);
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void editSchedule(int position) {
        ScheduleItem clickedItem = scheduleAdapter.getItem(position);

        Intent intent = new Intent(ScheduleActivity.this, EditScheduleActivity.class);
        intent.putExtra("EXTRA_SCHEDULE_ID", clickedItem);
        intent.putExtra("EXTRA_SCHEDULE_LIST", arrayList); // Pass the list of schedules
        startActivity(intent);
    }

    private void scheduleAlarm(ScheduleItem scheduleItem) {
        if (scheduleItem != null) {
            // ... (kode lainnya)

            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("SCHEDULE_ITEM", scheduleItem);

            // ... (kode lainnya)
        } else {
            Log.e("ScheduleActivity", "ScheduleItem is null when scheduling alarm");
        }
    }

}