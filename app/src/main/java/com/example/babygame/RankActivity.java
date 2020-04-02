package com.example.babygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RankActivity extends AppCompatActivity {
    ArrayList<Rank> ranklist= new ArrayList<>();
    RankAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        final SharedPreferences pref = getSharedPreferences("rank", 0);
        final SharedPreferences.Editor editor = pref.edit();

        ArrayList<Rank> gsonRank = new ArrayList<>();
        Gson gson = new GsonBuilder().create();


        String strJson = pref.getString("rank", null);
        if(strJson!=null){
            gsonRank = gson.fromJson(strJson, new TypeToken<ArrayList<Rank>>() {}.getType());
            ranklist=gsonRank;
            Log.d(this.toString(),strJson.toString());
        }
        else{
            Log.d(this.toString(),"null임 ");

        }


        adapter=new RankAdapter(ranklist);

        listView=(ListView)findViewById(R.id.lv_rank_list);
        listView.setAdapter(adapter);

        ImageButton backBtn=(ImageButton)findViewById(R.id.btn_rank_back);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
