package com.example.babygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class ResultActivity extends AppCompatActivity {

    TextView scoreTv, nameTv;
    Button mainBtn, rankBtn;
    String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        nameTv = (TextView) findViewById(R.id.et_result_name);

        scoreTv = (TextView) findViewById(R.id.tv_result_score);
        if (getIntent().hasExtra("score")) {
            score = getIntent().getStringExtra("score").toString();
            scoreTv.setText("점수: " + score);
        }

        mainBtn = (Button) findViewById(R.id.btn_result_main);
        rankBtn = (Button) findViewById(R.id.btn_result_rank);

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTv.getText().toString();

                final SharedPreferences pref = getSharedPreferences("rank", 0);
                final SharedPreferences.Editor editor = pref.edit();

                ArrayList<Rank> gsonRank = new ArrayList<>();
                Gson gson = new GsonBuilder().create();

                String strJson = pref.getString("rank", null);

                if (strJson != null) {
                    gsonRank = gson.fromJson(strJson, new TypeToken<ArrayList<Rank>>() {
                    }.getType());
                    gsonRank.add(new Rank(name,score));
                    Log.d(this.toString(),gsonRank.toString());

                }else{
                    gsonRank.add(new Rank(name,score));
                    Log.d(this.toString(),gsonRank.toString());
                }

                strJson=gson.toJson(gsonRank);
                Log.d(this.toString(),strJson);
                editor.putString("rank",strJson);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), RankActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
