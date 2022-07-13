package com.poco.loginpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivityTwo extends AppCompatActivity {

    public static final String usuarioName = "name";
    TextView TxvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        TxvUser = (TextView) findViewById(R.id.emailName);
        // mostrar el dato recogido
        String user = getIntent().getStringExtra("name");
        TxvUser.setText(user);
    }
}