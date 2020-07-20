package com.example.enterprisemobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText phoneInput;
    private EditText smsInput;
    private Button callBtn;
    private Button sendBtn;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        callByNumber();
        sendBySms();
    }

    private void sendBySms() {
        sendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!phoneInput.getText().toString().equals("") &&
                        !smsInput.getText().toString().equals("")) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS);
                    } else {
                        SmsManager smg = SmsManager.getDefault();

                        smg.sendTextMessage(phoneInput.getText().toString(),
                                null,
                                smsInput.getText().toString(),
                                null,
                                null);
                    }
                } else {
                    Toast.makeText(v.getContext(), "Заполните поля",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void callByNumber() {
        callBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!phoneInput.getText().toString().equals("")) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    } else {

                        String formatUri = "tel:" + phoneInput.getText();
                        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(formatUri));
                        startActivity(dialIntent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Укажите номер",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResult) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    callByNumber();
                } else {
                    finish();
                }
            }
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    sendBySms();
                } else {
                    finish();
                }
            }
        }
    }

    private void initView() {
        phoneInput = findViewById(R.id.phone_input);
        smsInput = findViewById(R.id.sms_input);
        callBtn = findViewById(R.id.call_button);
        sendBtn = findViewById(R.id.send_button);
    }
}