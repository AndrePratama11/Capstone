package com.example.capstone2project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.capstone2project.databinding.FoodDetailBinding;

import java.util.List;

/**
 * Activity untuk menampilkan detail makanan, termasuk informasi nutrisi dan deskripsi.
 */
public class FoodDetailActivity extends AppCompatActivity {

    // Binding objek untuk layout aktivitas
    private FoodDetailBinding binding;

    // Objek FoodData yang akan ditampilkan detailnya
    private FoodDataActivity foodData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mengatur layout dari file XML menggunakan view binding
        binding = FoodDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Mendapatkan Intent dari aktivitas sebelumnya
        Intent intent = getIntent();

        // Mengekstrak objek FoodData dari Intent
        if (intent != null && intent.hasExtra("FOOD_DATA")) {
            FoodDataActivity receivedFoodData = (FoodDataActivity) intent.getSerializableExtra("FOOD_DATA");

            // Menetapkan objek FoodData yang diterima ke variabel foodData
            foodData = receivedFoodData;
        }

        // Memeriksa apakah foodData tidak null sebelum menetapkan tampilan
        if (foodData != null) {
            setViewByData();
        }
    }

    /**
     * Metode untuk menetapkan tampilan detail makanan berdasarkan data FoodData.
     */
    private void setViewByData() {
        // Menetapkan data makanan ke elemen tampilan yang sesuai
        binding.foodname.setText(foodData.getNamaMakanan());
        binding.detailkarb.setText(foodData.getKarb());
        binding.detaillemak.setText(foodData.getLemak());
        binding.detailprotein.setText(foodData.getProtein());
        binding.detailcal.setText(foodData.getTotalCalories());
        binding.detaildescription.setText(foodData.getDescription());

        // Menggunakan Glide untuk memuat gambar makanan ke ImageView
        Glide.with(this)
                .load(foodData.getImg()) // Sumber daya gambar (URL, path file, atau ID resource)
                .into(binding.imgMakanan);
    }
}
