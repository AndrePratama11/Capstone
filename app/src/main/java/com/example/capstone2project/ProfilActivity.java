    package com.example.capstone2project;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    public class ProfilActivity extends AppCompatActivity {

        Button edtprofile;

        Button lgout;
        private TextView mailtxt, nametxt, heighttxt, weighttxt, genderspn;

        private DatabaseReference database;
        private FirebaseAuth firebaseAuth;


        @Override
        protected void onCreate(Bundle saveInstanceState){
            super.onCreate(saveInstanceState);
            setContentView(R.layout.profil);

            mailtxt=findViewById(R.id.edEmail);
            nametxt=findViewById(R.id.edNama);
            genderspn=findViewById(R.id.edgend);
            heighttxt=findViewById(R.id.edheight);
            weighttxt=findViewById(R.id.edweight);
            edtprofile=findViewById(R.id.edprof);
            lgout=findViewById(R.id.btnlogout);

            firebaseAuth=FirebaseAuth.getInstance();
            database= FirebaseDatabase.getInstance().getReference("users");

            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                String email = user.getEmail();
                mailtxt.setText(email);


                String userId = user.getUid();
                // Mendapatkan data pengguna dari database Firebase
                database.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            nametxt.setText(snapshot.child("username").getValue(String.class));
                            mailtxt.setText(email);
                            genderspn.setText(snapshot.child("gender").getValue(String.class));
                            heighttxt.setText(snapshot.child("height").getValue(String.class));
                            weighttxt.setText(snapshot.child("weight").getValue(String.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
            }

            edtprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), EditProfilActivity.class);
                    startActivity(intent);
                }
            });

            lgout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
        }

        @Override
        protected void onResume() {
            super.onResume();

            // Refresh data setelah kembali dari EditProfilActivity
            refreshUserData();
        }

        private void refreshUserData() {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                String email = user.getEmail();
                mailtxt.setText(email);

                String userId = user.getUid();

                // Mendapatkan data pengguna dari database Firebase
                database.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            nametxt.setText(snapshot.child("username").getValue(String.class));

                            genderspn.setText(snapshot.child("gender").getValue(String.class));
                            heighttxt.setText(snapshot.child("height").getValue(String.class));
                            weighttxt.setText(snapshot.child("weight").getValue(String.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
            }
        }
    }
