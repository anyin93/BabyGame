package com.example.babygame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

public class GameActivity extends AppCompatActivity {
    ImageButton btnMilk, btnToy, btnMobil, btnPad;
    ImageView[] img_array = new ImageView[9];
    int[] imageID = {R.id.iv_game_baby1, R.id.iv_game_baby2, R.id.iv_game_baby3, R.id.iv_game_baby4, R.id.iv_game_baby5
            , R.id.iv_game_baby6, R.id.iv_game_baby7, R.id.iv_game_baby8, R.id.iv_game_baby9};
    ImageButton start;
    TextView time, score;
    Thread thread = null;
    int timetmp;
    int scoretmp;


    final String TAG_OFF = "OFF";
    final String TAG_POO = "poo";
    final String TAG_SIMSIM = "simsim";
    final String TAG_SLEEP = "sleep";
    final String TAG_MILK = "pad";
    final String TAG_SMILE = "smile";

    //5초 증가 : 전체 time 변수를 두고 thread로 타이머 도는데 웃는애기 롱클릭 시 -> 그것에 5초를 더하고 저장한뒤 다시 스레드 시작
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btnMilk = (ImageButton) findViewById(R.id.btn_game_milk);
        btnToy = (ImageButton) findViewById(R.id.btn_game_toy);
        btnMobil = (ImageButton) findViewById(R.id.btn_game_mobil);
        btnPad = (ImageButton) findViewById(R.id.btn_game_pad);
        btnMilk.setTag("milk");
        btnToy.setTag("toy");
        btnMobil.setTag("mobil");
        btnPad.setTag("pad");

        timetmp = 60;
        scoretmp = 0;

        start = (ImageButton) findViewById(R.id.btn_game_start);
        time = (TextView) findViewById(R.id.tv_game_timer);
        score = (TextView) findViewById(R.id.tv_game_score);
        score.setText("-");
        time.setText("-- : --");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread = new Thread(new timeCheck());
                thread.start();
                score.setText("0");
                time.setText("00:00");


                for (int i = 0; i < img_array.length; i++) {
                    new Thread(new DThread(i)).start();
                }
            }
        });

        for (int i = 0; i < 9; i++) {
            img_array[i] = (ImageView) findViewById(imageID[i]);
            img_array[i].setImageResource(R.drawable.baby);
            img_array[i].setPadding(20, 20, 20, 20);
            img_array[i].setTag(TAG_OFF);

            img_array[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (((ImageView) v).getTag().toString().equals(TAG_SMILE)) {
                        timetmp += 5;
                        thread.interrupt();
                        thread = new Thread(new timeCheck());
                        thread.start();
                        Toast.makeText(getApplicationContext(), "5초 증가", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });

            img_array[i].setOnDragListener(mDragListener);
        }

        btnMilk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //클릭 시 클립데이터 만듬
                ClipData clip = ClipData.newPlainText("milk", "milk");

                //드래그 할 데이터, 섀도우 지정, 드래그 앤 드롭 관련 데이터를 가지는 객체지정, 0
                v.startDrag(clip, new View.DragShadowBuilder(v), null, 0);
                return false;
            }
        });
        btnToy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //클릭 시 클립데이터 만듬
                ClipData clip = ClipData.newPlainText("toy", "toy");

                //드래그 할 데이터, 섀도우 지정, 드래그 앤 드롭 관련 데이터를 가지는 객체지정, 0
                v.startDrag(clip, new View.DragShadowBuilder(v), null, 0);
                return false;
            }
        });
        btnMobil.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //클릭 시 클립데이터 만듬
                ClipData clip = ClipData.newPlainText("mobil", "mobil");

                //드래그 할 데이터, 섀도우 지정, 드래그 앤 드롭 관련 데이터를 가지는 객체지정, 0
                v.startDrag(clip, new View.DragShadowBuilder(v), null, 0);
                return false;
            }
        });
        btnPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //클릭 시 클립데이터 만듬
                ClipData clip = ClipData.newPlainText("pad", "pad");

                //드래그 할 데이터, 섀도우 지정, 드래그 앤 드롭 관련 데이터를 가지는 객체지정, 0
                v.startDrag(clip, new View.DragShadowBuilder(v), null, 0);
                return false;
            }
        });


    }

    View.OnDragListener mDragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            ImageView btn;

            //드래그 객체가 버튼인지 확인
            if (v instanceof ImageView) {
                Log.d(this.toString(), "드래그 객체가 이미지뷰임");
                btn = (ImageView) v;
            } else {
                Log.d(this.toString(), "드래그 객체가 이미지뷰 아님");
                return false;
            }

            switch (event.getAction()) {

                case DragEvent.ACTION_DROP:
                    Log.d(this.toString(), "드래그 드랍");
                    String text = event.getClipData().getItemAt(0).getText().toString();
                    if (btn.getTag().equals(TAG_MILK) && text.equals("milk")) {
                        scoretmp += 50;
                        score.setText(Integer.toString(scoretmp));
                        Toast.makeText(getApplicationContext(), "+50점", Toast.LENGTH_SHORT).show();

                    }
                    if (btn.getTag().equals(TAG_SLEEP) && text.equals("mobil")) {
                        scoretmp += 30;
                        score.setText(Integer.toString(scoretmp));
                        Toast.makeText(getApplicationContext(), "+30점", Toast.LENGTH_SHORT).show();
                    }
                    if (btn.getTag().equals(TAG_POO) && text.equals("pad")) {
                        scoretmp += 20;
                        score.setText(Integer.toString(scoretmp));
                        Toast.makeText(getApplicationContext(), "+20점", Toast.LENGTH_SHORT).show();

                    }
                    if (btn.getTag().equals(TAG_SIMSIM) && text.equals("toy")) {
                        scoretmp += 10;
                        score.setText(Integer.toString(scoretmp));
                        Toast.makeText(getApplicationContext(), "+10점", Toast.LENGTH_SHORT).show();

                    }

            }

            return true;
        }
    };

    @SuppressLint("HandlerLeak")
    Handler pooHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            img_array[msg.arg1].setPadding(0, 0, 0, 0);
            img_array[msg.arg1].setImageResource(R.drawable.poobaby);
            img_array[msg.arg1].setTag(TAG_POO);
        }
    };
    @SuppressLint("HandlerLeak")
    Handler milkHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            img_array[msg.arg1].setImageResource(R.drawable.milkbaby);
            img_array[msg.arg1].setPadding(0, 0, 0, 0);
            img_array[msg.arg1].setTag(TAG_MILK);
        }
    };
    @SuppressLint("HandlerLeak")
    Handler sleepHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            img_array[msg.arg1].setImageResource(R.drawable.sleepbaby);
            img_array[msg.arg1].setPadding(0, 0, 0, 0);
            img_array[msg.arg1].setTag(TAG_SLEEP);
        }
    };
    @SuppressLint("HandlerLeak")
    Handler simsimHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            img_array[msg.arg1].setImageResource(R.drawable.simsimbaby);
            img_array[msg.arg1].setPadding(0, 0, 0, 0);
            img_array[msg.arg1].setTag(TAG_SIMSIM);
        }
    };
    @SuppressLint("HandlerLeak")
    Handler smileHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            img_array[msg.arg1].setImageResource(R.drawable.smilebaby);
            img_array[msg.arg1].setPadding(20, 20, 20, 20);
            img_array[msg.arg1].setTag(TAG_SMILE);
        }
    };


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int sec = msg.arg1 % 60;
            int min = msg.arg1 / 60;
            String result = String.format("%02d:%02d", min, sec);
            time.setText(result);

            timetmp = sec + (min * 60);
            if (timetmp <= 5) {
                time.setTextColor(Color.parseColor("#CC3300"));
            }
        }
    };


    public class timeCheck implements Runnable {
        int time;

        @Override
        public void run() {


            for (time = timetmp; time >= 0; time--) {
                Message msg = new Message();
                msg.arg1 = time--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

                handler.sendMessage(msg);
            }


            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            String sumscore = score.getText().toString();
            intent.putExtra("score", sumscore);
            startActivity(intent);
            finish();
        }

    }

    public class DThread implements Runnable {

        int index = 0; //애기 번호

        public DThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            while (true) {
                Message msg1 = new Message();
                int offtime = new Random().nextInt(5000) + 500;
                int babytype = new Random().nextInt(9);
                try {
                    Thread.sleep(offtime); //애기가 변하는 시간 랜덤
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msg1.arg1 = index;
                switch (babytype) {
                    case 0:
                        pooHandler.sendMessage(msg1);
                        break;
                    case 1:
                        pooHandler.sendMessage(msg1);
                        break;
                    case 2:
                        simsimHandler.sendMessage(msg1);
                        break;
                    case 3:
                        simsimHandler.sendMessage(msg1);
                        break;
                    case 4:
                        sleepHandler.sendMessage(msg1);
                        break;
                    case 5:
                        sleepHandler.sendMessage(msg1);
                        break;
                    case 6:
                        milkHandler.sendMessage(msg1);
                        break;
                    case 7:
                        milkHandler.sendMessage(msg1);
                        break;
                    case 8:
                        smileHandler.sendMessage(msg1);
                        break;
                    default:
                        break;

                }


            }
        }


    }


}


