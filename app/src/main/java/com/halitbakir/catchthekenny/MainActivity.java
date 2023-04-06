package com.halitbakir.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Global Variable
    TextView scoreText;
    TextView timeText;
    int score;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = findViewById(R.id.timeText);
        scoreText = findViewById(R.id.scoreText);
        imageView=findViewById(R.id.imageView);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);
        imageView9=findViewById(R.id.imageView9);

        imageArray = new ImageView[] {imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};
        hideImages();

        score=0; //skor ilk başta sıfır kabul ettik

        //süreyi gerisayma
        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long l) {
                timeText.setText("Time: " + l/1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("Time off");
                handler.removeCallbacks(runnable); //runnable durdurma
                for (ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE); //her saniye tüm resimleri görünmez yaptık
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart?");
                alert.setMessage("Are you sure to restart game?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //restart
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();

            }
        }.start();
    }

    public void  increaseScore (View view){

        score++; //oncreate altında yazdık ve böylece kennye her tıklandığında skor 1 tane arttırılacak.
        scoreText.setText("Skor: " + score); //skor sonucunu ekrana yazdırma

    }

    public void hideImages() { //resimlerin kaybolması için
        handler= new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE); //her saniye tüm resimleri görünmez yaptık
                }

                Random random = new Random();
                int i = random.nextInt(9); //0-8 arası rastgele rakam getirme
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this,420);
            }
        };

        handler.post(runnable);

    }

}