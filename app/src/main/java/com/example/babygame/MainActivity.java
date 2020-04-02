package com.example.babygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton startBtn=(ImageButton)findViewById(R.id.btn_main_how);
        ImageButton gameBtn=(ImageButton)findViewById(R.id.btn_main_start);
        ImageButton rankBtn=(ImageButton)findViewById(R.id.btn_main_rank);

        startBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),HowtoActivity.class);
                startActivity(intent);
            }
        });

        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GameActivity.class);
                startActivity(intent);
            }
        });
        rankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RankActivity.class);
                startActivity(intent);
            }
        });
    }
}
