package com.example.capstone2project;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddScheduleActivity extends AppCompatActivity {

    private TextView name3, nametgl, waktu;
    private Spinner edJudullat;
    private EditText edtgl, edwaktu;
    private ImageButton btn_time, btn_date;
    private Button btn_save;

    private int jam, menit;
    private int jam2, menit2;
    private ScheduleAdapter scheduleAdapter;


    private int tanggal, bulan, tahun;
    private int tanggal2, bulan2, tahun2;
    private Uri selectedSoundUri;
    private static final int RINGTONE_PICKER_REQUEST_CODE = 1; // Anda dapat mengganti angka 1 dengan nilai yang sesuai


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule);

        name3 = findViewById(R.id.name3);
        nametgl = findViewById(R.id.nametgl);
        waktu = findViewById(R.id.waktu);
        edJudullat = findViewById(R.id.edJudullat);
        edtgl = findViewById(R.id.edtgl);
        edwaktu = findViewById(R.id.edwaktu);
        btn_time = findViewById(R.id.btn_time);
        btn_date = findViewById(R.id.btn_date);
        btn_save = findViewById(R.id.btn_save);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edJudullat.setAdapter(adapter);


        // Set listener untuk tombol jika dibutuhkan
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logika untuk menangani klik tombol Save
                saveSchedule();
            }
        });

        // Set listener untuk ImageButton jika dibutuhkan
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                jam = calendar.get(Calendar.HOUR_OF_DAY);
                menit = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog;
                dialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                dialog = new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        // Set listener untuk ImageButton jika dibutuhkan
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                tahun = calendar.get(Calendar.YEAR);
                bulan = calendar.get(Calendar.MONTH);
                tanggal = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(AddScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                dialog = new DatePickerDialog(AddScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    private void openSoundPicker() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Pilih Suara Notifikasi");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        startActivityForResult(intent, RINGTONE_PICKER_REQUEST_CODE);
    }



    // Metode untuk menangani penyimpanan jadwal
    // Metode untuk menangani penyimpanan jadwal
    private void saveSchedule() {
        // Ambil nilai dari elemen antarmuka pengguna dan simpan di database atau lakukan tindakan lainnya
        String judulLatihan = edJudullat.getSelectedItem().toString();
        String tanggal = edtgl.getText().toString();
        String waktu = edwaktu.getText().toString();

        // Dapatkan referensi database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("schedules");

        // Buat objek ScheduleItem untuk disimpan di database
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setJudul(judulLatihan);
        scheduleItem.setTanggal(tanggal);
        scheduleItem.setWaktu(waktu);

        // Atur soundUri dari data yang diperoleh dari pemilihan suara
        scheduleItem.setSoundUri(selectedSoundUri);

        // Simpan objek ScheduleItem ke Firebase Realtime Database
        String key = myRef.push().getKey(); // Dapatkan kunci baru
        scheduleItem.setKey(key); // Set kunci di objek ScheduleItem
        myRef.child(key).setValue(scheduleItem);

        // ... (kode lain untuk menampilkan notifikasi dan sebagainya)

        // Setelah menyimpan, arahkan ke ScheduleActivity
        Intent intent = new Intent(AddScheduleActivity.this, ScheduleActivity.class);
        startActivity(intent);

        // Akhiri activity saat ini (AddScheduleActivity) agar pengguna tidak dapat kembali ke layar ini
        finish();

        // Setelah menyimpan objek ScheduleItem ke Firebase Realtime Database

        // Set waktu untuk alarm (gunakan Calendar untuk mengatur waktu)
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, tahun);
        calendar.set(Calendar.MONTH, bulan2);
        calendar.set(Calendar.DAY_OF_MONTH, tanggal2);
        calendar.set(Calendar.HOUR_OF_DAY, jam2);
        calendar.set(Calendar.MINUTE, menit2);
        calendar.set(Calendar.SECOND, 0);

        // Buat intent untuk AlarmReceiver
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        // Sertakan data tambahan ke intent jika diperlukan
        alarmIntent.putExtra("SCHEDULE_ITEM", scheduleItem);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Dapatkan AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Atur alarm
        if (alarmManager != null) {
            // Atur alarm dengan metode yang diinginkan (misalnya, setExact untuk alarm yang presisi)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        // ID untuk channel notifikasi (harus unik)
        String CHANNEL_ID = "my_channel_01";

        // Intent untuk menavigasikan ke aplikasi Anda ketika notifikasi ditekan
        Intent notificationIntent = new Intent(this, ScheduleActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // Membangun notifikasi
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Judul Notifikasi")
                .setContentText("Isi notifikasi")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)  // Intent yang akan dijalankan saat notifikasi ditekan
                .setAutoCancel(true);  // Notifikasi akan dihapus saat ditekan

        // ... (kode lain untuk menambahkan suara pada notifikasi jika diperlukan)
    }


    private String getSoundName(Uri soundUri) {
        Ringtone ringtone = RingtoneManager.getRingtone(this, soundUri);
        return ringtone.getTitle(this);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RINGTONE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            // Dapatkan URI suara yang dipilih
            selectedSoundUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            // Tampilkan informasi suara yang dipilih atau lakukan tindakan lainnya
            if (selectedSoundUri != null) {
                // Misalnya, tampilkan nama file suara
                String soundName = getSoundName(selectedSoundUri);
                showToast("Suara dipilih: " + soundName);
            }
        }
    }
}

