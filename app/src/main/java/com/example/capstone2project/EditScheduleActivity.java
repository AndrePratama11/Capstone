package com.example.capstone2project;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditScheduleActivity extends AppCompatActivity {

    private Spinner edJudullat;
    private EditText edtgl, edwaktu;
    private Button btn_save, btn_delete;
    private ImageButton btn_time, btn_date;
    private int jam, menit;
    private int jam2, menit2;
    private int tanggal, bulan, tahun;
    private int tanggal2, bulan2, tahun2;

    private ScheduleItem editedItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_schedule);

        edJudullat = findViewById(R.id.edJudullat);
        edtgl = findViewById(R.id.edtgl);
        edwaktu = findViewById(R.id.edwaktu);
        btn_save = findViewById(R.id.btn_save);
        btn_time = findViewById(R.id.btn_time);
        btn_date = findViewById(R.id.btn_date);
        btn_delete = findViewById(R.id.btn_delete);

        Intent intent2 = getIntent();
        // Add this line in onCreate after retrieving editedItem
        ArrayList<ScheduleItem> scheduleList = intent2.getParcelableArrayListExtra("EXTRA_SCHEDULE_LIST");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edJudullat.setAdapter(adapter);

        // Mendapatkan objek ScheduleItem yang diklik dari intent
        Intent intent = getIntent();
        editedItem = intent.getParcelableExtra("EXTRA_SCHEDULE_ID");

        Log.d("EditScheduleActivity", "Key:" + editedItem.getKey());

        // Mengatur nilai default pada tampilan berdasarkan item yang diklik
        if (editedItem != null) {
            Log.d("EditScheduleActivity", "Key:" + editedItem.getKey());
            edJudullat.setSelection(getIndex(edJudullat, editedItem.getJudul()));
            edtgl.setText(editedItem.getTanggal());
            edwaktu.setText(editedItem.getWaktu());
        } else {
            Log.e("EditScheduleActivity", "editedItem is null");
        }

        // Set listener untuk tombol Save
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logika untuk menyimpan perubahan ke database atau melakukan tindakan lainnya
                saveEditedSchedule();
            }
        });

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                jam = calendar.get(Calendar.HOUR_OF_DAY);
                menit = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog;
                dialog = new TimePickerDialog(EditScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jam = hourOfDay;
                        menit = minute;

                        if (jam <= 12) {
                            edwaktu.setText(String.format(Locale.getDefault(), "%d:%d am", jam, menit));
                        }else {
                            edwaktu.setText(String.format(Locale.getDefault(), "%d:%d pm", jam, menit));
                        }
                    }
                }, jam, menit, true);
                dialog.show();
            }
        });
        edwaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                jam2 = calendar.get(Calendar.HOUR_OF_DAY);
                menit2 = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog;
                dialog = new TimePickerDialog(EditScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jam2 = hourOfDay;
                        menit2 = minute;

                        if (jam <= 12) {
                            edwaktu.setText(String.format(Locale.getDefault(), "%d:%d am", jam2, menit2));
                        }else {
                            edwaktu.setText(String.format(Locale.getDefault(), "%d:%d pm", jam2, menit2));
                        }
                    }
                }, jam2, menit2, true);
                dialog.show();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menghapus data
                deleteSchedule();
                // Logika untuk menghapus data dari database
                moveToHistory(scheduleList);
            }
        });

        // Set listener untuk ImageButton jika dibutuhkan
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                tahun = calendar.get(Calendar.YEAR);
                bulan = calendar.get(Calendar.MONTH);
                tanggal = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(EditScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tahun = year;
                        bulan = month;
                        tanggal = day;

                        edtgl.setText(tanggal + " - " + (bulan + 1) + " - " + tahun);
                    }
                }, tahun,bulan,tanggal);
                dialog.show();
            }
        });

        edtgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                tahun2 = calendar.get(Calendar.YEAR);
                bulan2 = calendar.get(Calendar.MONTH);
                tanggal2 = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(EditScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tahun2 = year;
                        bulan2 = month;
                        tanggal2 = day;

                        edtgl.setText(tanggal2 + " - " + (bulan2 + 1) + " - " + tahun2);
                    }
                }, tahun2,bulan2,tanggal2);
                dialog.show();
            }
        });
    }

    private void saveEditedSchedule() {
        // Ambil nilai dari elemen antarmuka pengguna dan simpan di database atau lakukan tindakan lainnya
        String judulLatihan = edJudullat.getSelectedItem().toString();
        String tanggal = edtgl.getText().toString();
        String waktu = edwaktu.getText().toString();

        editedItem.setJudul(judulLatihan);
        editedItem.setTanggal(tanggal);
        editedItem.setWaktu(waktu);

        scheduleAlarm(editedItem);
//        ScheduleItem editedItem = new ScheduleItem(judulLatihan, tanggal, waktu);
//        editedItem.setJudul(editedItem.getJudul()); // Set the key from the original item

        // Update or add data to Firebase using the key
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("schedules");

        if (editedItem.getKey() != null) {
            myRef.child(editedItem.getKey()).setValue(editedItem);
            Log.d("EditScheduleActivity", "Data updated successfully");
        } else {
            Log.e("EditScheduleActivity", "editedItem.getKey() is null");
        }

        Intent intent = new Intent(EditScheduleActivity.this, ScheduleActivity.class);
        startActivity(intent);

        // After finishing, you can close this activity or perform other actions
        finish();
    }

    private void scheduleAlarm(ScheduleItem scheduleItem) {
        // Use AlarmManager to schedule the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Create an intent for the BroadcastReceiver
        Intent intent = new Intent(this, AlarmReceiver.class);
        // Gunakan soundUri dari scheduleItem jika tersedia, jika tidak gunakan nada dering alarm default
        Uri soundUri = scheduleItem.getSoundUri();
        if (soundUri != null) {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, soundUri.toString());
        } else {
            // Gunakan nada dering alarm default
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, defaultSoundUri.toString());
        }
        intent.putExtra("SCHEDULE_ITEM", scheduleItem);


        // Create a PendingIntent with FLAG_UPDATE_CURRENT
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm time (you may need to adjust this based on your requirements)
        Calendar calendar = Calendar.getInstance();
        String[] timeParts = scheduleItem.getWaktu().split(":");

        try {
            int hour = Integer.parseInt(timeParts[0].trim());
            String[] hourAndPeriod = timeParts[0].split(" ");

            // Periksa apakah jam melebihi 12 dan menggunakan format "pm"
            if (hourAndPeriod.length > 1) {
                String period = hourAndPeriod[1].toLowerCase();
                if (period.equals("pm") && hour < 12) {
                    hour += 12;
                } else if (period.equals("am") && hour == 12) {
                    hour = 0;
                }
            }

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1].trim()));
            calendar.set(Calendar.SECOND, 0);

            // Logging scheduled time
            Log.d("EditScheduleActivity", "Scheduled time in milliseconds: " + calendar.getTimeInMillis());

            // Schedule the alarm
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            // Handle the exception, log an error, or show a user-friendly message
        }
    }



    private void moveToHistory(ArrayList<ScheduleItem> scheduleList) {
        // Assuming the history node is under the same parent in the database
        DatabaseReference historyReference = FirebaseDatabase.getInstance().getReference("history");

        // Iterate through the scheduleList and move each item to history
        for (ScheduleItem scheduleItem : scheduleList) {
            // Create a new key for the history entry
            String historyKey = historyReference.push().getKey();

            // Set the key for the schedule item and push it to history
            scheduleItem.setKey(historyKey);
            historyReference.child(historyKey).setValue(scheduleItem);

            // Remove the schedule item from the schedules node
            DatabaseReference scheduleReference = FirebaseDatabase.getInstance().getReference("schedules");
            scheduleReference.child(scheduleItem.getKey()).removeValue();
        }

        // Notify the user or perform any other necessary actions
        Toast.makeText(this, "Schedules moved to history", Toast.LENGTH_SHORT).show();
    }

    private void deleteSchedule() {
        // Dapatkan referensi database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("schedules");

        if (editedItem.getKey() != null) {
            // Hapus data dari Firebase berdasarkan kunci (key)
            myRef.child(editedItem.getKey()).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("EditScheduleActivity", "Data deleted successfully");
                            Intent intent = new Intent(EditScheduleActivity.this, ScheduleActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("EditScheduleActivity", "Error deleting data: " + e.getMessage());
                            // Handle error, if any
                        }
                    });
        } else {
            Log.e("EditScheduleActivity", "editedItem.getKey() is null");
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                return i;
            }
        }
        return 0;
    }
}
