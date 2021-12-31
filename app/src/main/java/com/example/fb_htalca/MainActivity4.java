package com.example.fb_htalca;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {

    public ImageView fotof;
    public TextView textof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        fotof= findViewById(R.id.fotof);
        textof = findViewById(R.id.textof);

        fotof.setImageURI(Uri.parse((getIntent().getStringExtra("foto"))));
        textof.setText(getIntent().getStringExtra("texto"));

    }
}