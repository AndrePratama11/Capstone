package com.example.capstone2project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText edEmail, edPass;
    private Button btnLogin, btnRegist;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Inisialisasi elemen UI
        edEmail = findViewById(R.id.edNama);
        edPass = findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.blogin);
        btnRegist = findViewById(R.id.bregist);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = edEmail.getText().toString();
                String pwd = edPass.getText().toString();

                if (mail.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Masukkan Email dan Password", Toast.LENGTH_LONG).show();
                } else {
                    // Gunakan Firebase Authentication untuk login
                    firebaseAuth.signInWithEmailAndPassword(mail, pwd)
                            .addOnCompleteListener(MainActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Login berhasil
                                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent masuk = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(masuk);
                                } else {
                                    // Jika login gagal
                                    Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Cek apakah pengguna sudah login pada saat activity dimulai
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // Jika sudah login, arahkan ke activity Home
            Intent masuk = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(masuk);
            finish(); // Tutup activity ini agar tidak dapat dikembalikan dengan tombol back
        }
    }
}
