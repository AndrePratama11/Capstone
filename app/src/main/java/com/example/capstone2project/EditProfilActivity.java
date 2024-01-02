package com.example.capstone2project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfilActivity extends AppCompatActivity {
    private EditText nametxt, heighttxt, weighttxt;
    private TextView mailtxt;
    private Spinner genderSpin;
    private Button saveBtn;

    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profil);

        mailtxt = findViewById(R.id.edEmail2);
        nametxt = findViewById(R.id.edNama2);
        heighttxt = findViewById(R.id.edheight2);
        weighttxt = findViewById(R.id.edweight2);
        genderSpin = findViewById(R.id.spinnerGender2);
        saveBtn = findViewById(R.id.svbutton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpin.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users");

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
            mailtxt.setText(email);

            String userId = user.getUid();

            // Mendapatkan data pengguna dari database Firebase untuk menampilkan data awal
            database.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        nametxt.setText(snapshot.child("username").getValue(String.class));
                        heighttxt.setText(snapshot.child("height").getValue(String.class));
                        weighttxt.setText(snapshot.child("weight").getValue(String.class));

                        String gender = snapshot.child("gender").getValue(String.class);
                        if (gender != null) {
                            int spinnerPosition = adapter.getPosition(gender);
                            genderSpin.setSelection(spinnerPosition);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        }

        saveBtn.setOnClickListener(view -> {
            // Mengambil data yang diinputkan
            String newName = nametxt.getText().toString();
            String newGender = genderSpin.getSelectedItem().toString();
            String newHeight = heighttxt.getText().toString();
            String newWeight = weighttxt.getText().toString();

            if (user != null) {
                String userId = user.getUid();

                // Menyimpan data ke Firebase setelah menekan tombol simpan
                database.child(userId).child("username").setValue(newName);
                database.child(userId).child("gender").setValue(newGender);
                database.child(userId).child("height").setValue(newHeight);
                database.child(userId).child("weight").setValue(newWeight);

                Toast.makeText(EditProfilActivity.this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
                finish(); // Menutup activity setelah menyimpan perubahan
            }
        });
    }
}
