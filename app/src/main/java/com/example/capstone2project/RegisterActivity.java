package com.example.capstone2project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText edtNama, edtMail, edtPassword;
    Button reg;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.register);

        edtNama = findViewById(R.id.ednama);
        edtMail = findViewById(R.id.edemail);
        edtPassword = findViewById(R.id.edpassword);

        reg = findViewById(R.id.bregist);

        firebaseAuth = FirebaseAuth.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtMail.getText().toString();
                String username = edtNama.getText().toString();
                String password = edtPassword.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Snackbar.make(view, "Semua data harus di isi...", Snackbar.LENGTH_LONG).show();
                } else {
                    // Gunakan Firebase Authentication untuk registrasi pengguna
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Registrasi berhasil
                                    Toast.makeText(getApplicationContext(), "Registrasi berhasil, Silahkan Login kembali.", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(i);
                                    finish(); // Tutup activity ini agar tidak dapat dikembalikan dengan tombol back
                                } else {
                                    // Jika registrasi gagal
                                    Toast.makeText(getApplicationContext(), "Registrasi gagal: " + task.getException(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }
}
