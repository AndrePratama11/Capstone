package com.example.capstone2project;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Dapatkan item jadwal
        ScheduleItem scheduleItem = intent.getParcelableExtra("SCHEDULE_ITEM");

        if (scheduleItem != null) {
            // Putar suara
            Uri soundUri = Uri.parse(intent.getStringExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI));
            MediaPlayer mediaPlayer = MediaPlayer.create(context, soundUri);

            if (mediaPlayer != null) {
                mediaPlayer.start();
            }

            // Anda juga dapat menampilkan notifikasi atau melakukan tindakan lain di sini
        }
    }
}
