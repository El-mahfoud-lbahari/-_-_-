package com.mahtiz.mansayarbah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

public class choiseOfDomain extends AppCompatActivity {
//Button cultBtn,sportBtn,islamBtn,randBtn;
public static String domain;
//CardView cultBtn,sportBtn,islamBtn,awasimBtn;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_of_domain);
        /*AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "IMG_16_9_APP_INSTALL#585688515441540_585688912108167", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_cont);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();*/


        //cultBtn=findViewById(R.id.cultureBtn);
        //sportBtn=findViewById(R.id.sportBtn);
        //islamBtn=findViewById(R.id.islamBtn);
        //awasimBtn=findViewById(R.id.awasimBtn);
       /* cultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ActivityLevel.class);
                domain="culture";
                //intent.putExtra("domain","culture");
                startActivity(intent);
            }
        });*/
    /*    sportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ActivityLevel.class);
                //intent.putExtra("domain","sport");
                domain="sport";
                startActivity(intent);
            }
        });*/
        /*islamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ActivityLevel.class);
               // intent.putExtra("domain","islam");
                domain="islam";
                startActivity(intent);
            }
        });*/
      /* awasimBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(),ActivityLevel.class);
               domain="awasim";
               startActivity(intent);
           }
       });*/
    }
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    public void OnClickAwasim(View view) {
        Intent intent=new Intent(this,ActivityLevel.class);
        domain="awasim";
        startActivity(intent);
    }

    public void OnClickSport(View view) {
        Intent intent=new Intent(this,ActivityLevel.class);
        //intent.putExtra("domain","sport");
        domain="sport";
        startActivity(intent);

    }

    public void OnClickIslam(View view) {
        Intent intent=new Intent(getApplicationContext(),ActivityLevel.class);
        // intent.putExtra("domain","islam");
        domain="islam";
        startActivity(intent);
    }

    public void OnClickCulture(View view) {

        Intent intent=new Intent(getApplicationContext(),ActivityLevel.class);
        domain="culture";
        //intent.putExtra("domain","culture");
        startActivity(intent);
    }
}