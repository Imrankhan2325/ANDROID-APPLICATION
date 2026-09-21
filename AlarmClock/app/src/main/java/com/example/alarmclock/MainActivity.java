package com.example.alarmclock;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView tvTime;
    private Button btnSelectTime;
    private Button btnSetAlarm;

    private int hour;
    private int minute;

    private boolean timeSelected = false;

    private static final int NOTIFICATION_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tvTime);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);

        requestNotificationPermission();

        btnSelectTime.setOnClickListener(v -> selectTime());

        btnSetAlarm.setOnClickListener(v -> {

            if (!timeSelected) {

                Toast.makeText(
                        MainActivity.this,
                        "Please select alarm time first",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                setAlarm();
            }
        });
    }

    private void requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.POST_NOTIFICATIONS
                        },
                        NOTIFICATION_PERMISSION_CODE
                );
            }
        }
    }

    private void selectTime() {

        Calendar currentTime = Calendar.getInstance();

        int currentHour =
                currentTime.get(Calendar.HOUR_OF_DAY);

        int currentMinute =
                currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog =
                new TimePickerDialog(
                        MainActivity.this,

                        (view, selectedHour, selectedMinute) -> {

                            hour = selectedHour;
                            minute = selectedMinute;

                            timeSelected = true;

                            tvTime.setText(
                                    String.format(
                                            "Alarm Time: %02d:%02d",
                                            hour,
                                            minute
                                    )
                            );
                        },

                        currentHour,
                        currentMinute,
                        true
                );

        timePickerDialog.show();
    }

    private void setAlarm() {

        AlarmManager alarmManager =
                (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        calendar.set(
                Calendar.HOUR_OF_DAY,
                hour
        );

        calendar.set(
                Calendar.MINUTE,
                minute
        );

        calendar.set(
                Calendar.SECOND,
                0
        );

        calendar.set(
                Calendar.MILLISECOND,
                0
        );

        if (calendar.getTimeInMillis()
                <= System.currentTimeMillis()) {

            calendar.add(
                    Calendar.DAY_OF_YEAR,
                    1
            );
        }

        Intent intent =
                new Intent(
                        MainActivity.this,
                        AlarmReceiver.class
                );

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        MainActivity.this,
                        100,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.S) {

            if (!alarmManager.canScheduleExactAlarms()) {

                try {

                    Intent settingsIntent =
                            new Intent(
                                    Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                            );

                    startActivity(settingsIntent);

                    Toast.makeText(
                            this,
                            "Please allow Alarms & reminders permission",
                            Toast.LENGTH_LONG
                    ).show();

                } catch (Exception e) {

                    Toast.makeText(
                            this,
                            "Please enable Alarms & reminders in Settings",
                            Toast.LENGTH_LONG
                    ).show();
                }

                return;
            }
        }

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );

        Toast.makeText(
                MainActivity.this,
                "Alarm Set for "
                        + String.format(
                        "%02d:%02d",
                        hour,
                        minute
                ),
                Toast.LENGTH_LONG
        ).show();
    }
}
