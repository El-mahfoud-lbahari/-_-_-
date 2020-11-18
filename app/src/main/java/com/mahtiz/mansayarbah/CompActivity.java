package com.mahtiz.mansayarbah;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AudienceNetworkAds;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import com.facebook.ads.*;
public class CompActivity extends AppCompatActivity {
    int[] etatScoor=new int[]{8,13,18,23,27,28,37,41,48,58,78};
   // int[] etatScoor=new int[]{2,2,2,2,3,3,2,2,2,2,2};
      int[] questNbr=new int[]{10,15,20,25,30,30,40,45,50,60,82};
    int scoore=0;
    TextView questionTv,questionTv2,soal,nbPoint;
    Button choice1Btn,choice2Btn,choice3Btn,choice4Btn,getChoice1Btn,callFren;
    MediaPlayer clapPlayer ;
    MediaPlayer failPlayer;
    MediaPlayer AttentPlayer;
    Button[]choiceBtn=new Button[4];
    ImageView qPhoto;
    TextView scooreValueTv;
    Question q;
    TextView nameTv;
    List<Question> questions;
    ProgressBar progressBar;
    MyTimer t;
    String filename;
    int position=0;
    int nbQuest;
    SQLiteDatabase dbw,dbr;
    private InterstitialAd interstitialAd;
    private final String TAG = MainActivity.class.getSimpleName();
    private AdView adView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp);
        ///////////////////////////////////////////////////////////////////
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "616103192399372_616104669065891", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_conta);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();
        ////////////////////////////////////////////////////////////////
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "616103192399372_616112502398441");
        //////////////////////////////////////////////////////////////////
        dbw=MainActivity.helper.getWritableDatabase();
        nameTv = findViewById(R.id.nameTv);
        questionTv = findViewById(R.id.questionTv);
        questionTv2=findViewById(R.id.questionTv2);
        scooreValueTv = findViewById(R.id.scoreValueTv);
        choice1Btn = findViewById(R.id.opt1btn);
        choice2Btn = findViewById(R.id.opt2btn);
        choice3Btn = findViewById(R.id.op3btn);
        choice4Btn=findViewById(R.id.op4btn);
        nbPoint=findViewById(R.id.scoreTv);
        soal=findViewById(R.id.soal);
        questionTv.setTypeface(Typeface.createFromAsset(getAssets(),"AraHalaBoShesha-Regular.otf"));
        questionTv2.setTypeface(Typeface.createFromAsset(getAssets(),"AraHalaBoShesha-Regular.otf"));
        nameTv.setTypeface(Typeface.createFromAsset(getAssets(),"DG-Ghayaty-Thin-F.ttf"));
        soal.setTypeface(Typeface.createFromAsset(getAssets(),"DG-Ghayaty-Thin-F.ttf"));
        nbPoint.setTypeface(Typeface.createFromAsset(getAssets(),"DG-Ghayaty-Thin-F.ttf"));
        scooreValueTv.setTypeface(Typeface.createFromAsset(getAssets(),"DG-Ghayaty-Thin-F.ttf"));
        choice1Btn.setTypeface(Typeface.createFromAsset(getAssets(),"Co-Text-Regular.otf"));
        choice2Btn.setTypeface(Typeface.createFromAsset(getAssets(),"Co-Text-Regular.otf"));
        choice3Btn.setTypeface(Typeface.createFromAsset(getAssets(),"Co-Text-Regular.otf"));
        choice4Btn.setTypeface(Typeface.createFromAsset(getAssets(),"Co-Text-Regular.otf"));
        callFren=findViewById(R.id.callFren);
        qPhoto = findViewById(R.id.qPhoto);
        choiceBtn[0] = choice1Btn;
        choiceBtn[1] = choice2Btn;
        choiceBtn[2] = choice3Btn;
        choiceBtn[3] = choice4Btn;
        t=new MyTimer(20000,1000);
        progressBar=findViewById(R.id.progressBar);
        ///////////////////////////////////////////////////////////////////////////////
        clapPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clap);
        failPlayer = MediaPlayer.create(getApplicationContext(), R.raw.fail);
       // AttentPlayer=MediaPlayer.create(getApplicationContext(), R.raw.attent);
        clapPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCompletion(MediaPlayer mp) {
                //AttentPlayer.stop();
                loadNextQuestion();
                adView.loadAd();
            }
        });
        failPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCompletion(MediaPlayer mp) {
                //AttentPlayer.stop();
                loadNextQuestion();
                adView.loadAd();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////
        if (getIntent() != null && getIntent().hasExtra("choice")) {
            filename = getIntent().getStringExtra("choice");
             position=getIntent().getIntExtra("position",-1);
            //nameTv.setText(position+" "+filename);
            //nameTv.setText(MainActivity.nameOfJoeur);
            scooreValueTv.setText(scoore + " ");
        }
        //////////////////////////////////////////////////////////////////////////////////////
        try {
            QuestReader qReader = new QuestReader(this);
           questions = qReader.getQuetions(filename+".txt");
            Collections.shuffle(questions);
            loadNextQuestion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void loadNextQuestion(){
        if(questions.isEmpty()){
            if(scoore>=etatScoor[position-1]){
               ContentValues cv=new ContentValues();
                cv.put("etatLevel","pass");
              //  dbw.update(choiseOfDomain.domain+"Level",cv,"_id=?",new String[]{position+""});
                int pos=position+1;
                dbw.update("cultureLevel",cv,"_id=?",new String[]{pos+""});
                final Intent i=new Intent(getApplicationContext(),ActivityLevel.class);
                Toast.makeText(getApplicationContext(),"هنيئا لقد مررت إلى المستوى التالي",Toast.LENGTH_LONG).show();
                interstitialAd = new InterstitialAd(this, "616103192399372_616112502398441");
                // Set listeners for the Interstitial Ad
                interstitialAd.setAdListener(new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {
                        // Interstitial ad displayed callback
                        Log.e(TAG, "Interstitial ad displayed.");
                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        // Interstitial dismissed callback
                        Log.e(TAG, "Interstitial ad dismissed.");

                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Interstitial ad is loaded and ready to be displayed
                        Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                        // Show the ad
                        interstitialAd.show();

                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                        Log.d(TAG, "Interstitial ad clicked!");

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Ad impression logged callback
                        Log.d(TAG, "Interstitial ad impression logged!");

                    }
                });

                // For auto play video ads, it's recommended to load the ad
                // at least 30 seconds before it is shown
                interstitialAd.loadAd();
                startActivity(i);
            }else if(position<=11) {
                //Toast.makeText(getApplicationContext(),"لم ييمكن من الوصول إلى الستوى الموالي أعد المحاولة مرة أخرى.عليك تحقيق"+etatScoor[position]+"من ىالقاط في هذا المستوى",Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("تنبيه");
                builder.setMessage("لم تتمكن من اجتياز هذه المرحلة يجب عليك تحقيق"+" "+etatScoor[position-1]+" "+"نقطة في هذه المرحلة .أعد المحاولة مرة أخرى");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        interstitialAd = new InterstitialAd(getApplicationContext(), "616103192399372_616112502398441");
                        // Set listeners for the Interstitial Ad
                        interstitialAd.setAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {
                                // Interstitial ad displayed callback
                                Log.e(TAG, "Interstitial ad displayed.");
                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                                // Interstitial dismissed callback
                                Log.e(TAG, "Interstitial ad dismissed.");

                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {
                                // Ad error callback
                                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                // Interstitial ad is loaded and ready to be displayed
                                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                                // Show the ad
                                interstitialAd.show();

                            }

                            @Override
                            public void onAdClicked(Ad ad) {
                                // Ad clicked callback
                                Log.d(TAG, "Interstitial ad clicked!");

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {
                                // Ad impression logged callback
                                Log.d(TAG, "Interstitial ad impression logged!");

                            }
                        });

                        // For auto play video ads, it's recommended to load the ad
                        // at least 30 seconds before it is shown
                        interstitialAd.loadAd();
                        Intent i=new Intent(getApplicationContext(),ActivityLevel.class);
                        startActivity(i);
                    }
                });
                AlertDialog d=builder.create();
                d.show();
            }else if(!this.isFinishing()){
            Intent i=new Intent(getApplicationContext(),finalActivity.class);
            //i.putExtra("name",nameTv.getText().toString());
            //i.putExtra("scoore",scooreValueTv.getText().toString());
                interstitialAd = new InterstitialAd(this, "616103192399372_616112502398441");
                // Set listeners for the Interstitial Ad
                interstitialAd.setAdListener(new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {
                        // Interstitial ad displayed callback
                        Log.e(TAG, "Interstitial ad displayed.");
                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        // Interstitial dismissed callback
                        Log.e(TAG, "Interstitial ad dismissed.");

                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Interstitial ad is loaded and ready to be displayed
                        Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                        // Show the ad
                        interstitialAd.show();

                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                        Log.d(TAG, "Interstitial ad clicked!");

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Ad impression logged callback
                        Log.d(TAG, "Interstitial ad impression logged!");

                    }
                });

                // For auto play video ads, it's recommended to load the ad
                // at least 30 seconds before it is shown
                interstitialAd.loadAd();
            startActivity(i);
            }
            return;
        }
            q = questions.remove(0);

            for (Button btn : choiceBtn) {
                btn.setEnabled(true);
                btn.setBackgroundResource(R.drawable.choice_default_chip);
            }
           // AttentPlayer.start();
            questionTv.setText(q.getQuestionTest());
            questionTv2.setText("");
            nbQuest++;
            nameTv.setText(nbQuest+"/"+questNbr[position-1]);
            Collections.shuffle(q.getChoices());
            for (int i = 0; i < choiceBtn.length; i++) {
                final int j = i;
                choiceBtn[i].setText(q.getChoices().get(i));
                choiceBtn[i].setBackgroundResource(R.drawable.choice_default_chip);
                choiceBtn[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (choiceBtn[j].getText().toString().equalsIgnoreCase(q.getCorrectAnswer())) {
                            choiceBtn[j].setBackgroundResource(R.drawable.choice_correct_chip);
                            scoore += 1;
                            scooreValueTv.setText(scoore + " ");
                            for (Button btn : choiceBtn) {
                                btn.setEnabled(false);
                            }
                            t.cancel();
                            clapPlayer.start();
                        } else {
                            choiceBtn[j].setBackgroundResource(R.drawable.choice_erreur_chip);
                            for (Button btn : choiceBtn) {
                                btn.setEnabled(false);
                            }
                            t.cancel();
                            failPlayer.start();
                        }
                    }
                });
            }
            if (!q.getPhoto().equalsIgnoreCase("no image")) {
                int dotLoc = q.getPhoto().lastIndexOf(".");
                //String photoName = q.getPhoto().substring(0, dotLoc);
                String photoName = q.getPhoto();
                int photoId = getResources().getIdentifier(photoName, "drawable", getPackageName());
             try{   qPhoto.setImageResource(photoId);}catch(Exception e){

             }
            }else{
                qPhoto.setImageResource(0);
                questionTv2.setTextSize(24);
                questionTv2.setText(q.getQuestionTest());
                questionTv.setText("");
            }
            t.start();
    }

    public void callFriend(View view) {
        ContentValues cv=new ContentValues();
        dbr = MainActivity.helper.getReadableDatabase();
//        Cursor cursor = dbr.rawQuery("select * from "+choiseOfDomain.domain+"Level"+ " where"+" _id=?", new String[]{1 + ""});
        Cursor cursor = dbr.rawQuery("select * from cultureLevel where"+" _id=?", new String[]{position + ""});
        cursor.moveToFirst();
        double nbrCall=  cursor.getDouble(cursor.getColumnIndex("nbrCall"));
       AlertDialog.Builder builder=new AlertDialog.Builder(this);
        int j=0;
        if(nbrCall !=0){
            builder.setTitle("الصديق");
        for (int i = 0; i < choiceBtn.length; i++) {
            if (choiceBtn[i].getText().toString().equalsIgnoreCase(q.getCorrectAnswer())) {
               j=i+1;
            }
        }
        builder.setMessage("الجواب الصحيح هوالإقتراح رقم "+" "+j);
        builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog d=builder.create();
        d.show();
            nbrCall--;
            Toast.makeText(getApplicationContext(), "بقيت لديك "+" " + nbrCall+" "+"محولات", Toast.LENGTH_LONG).show();
            cv.put("nbrCall",nbrCall);
            dbw.update("cultureLevel",cv,"_id=?",new String[]{position+""});
        }else{
            callFren.setBackgroundResource(android.R.drawable.stat_sys_vp_phone_call);
            builder.setTitle("تنبيه");
                builder.setMessage("لقد نفذت جميع المحاولات المسموح بها .شارك التطبيق مع أحد أصدقائك لكي تكسب المزيد");
                builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog d=builder.create();
                d.show();
        }

    }

    public void deltAns(View view) {
        ContentValues cv=new ContentValues();
        dbr = MainActivity.helper.getReadableDatabase();
        //Cursor cursor = dbr.rawQuery("select * from "+choiseOfDomain.domain+"Level"+ " where"+" _id=?", new String[]{1 + ""});
        Cursor cursor = dbr.rawQuery("select * from cultureLevel where"+" _id=?", new String[]{position + ""});
        cursor.moveToFirst();
        double nbrDelAns=  cursor.getDouble(cursor.getColumnIndex("nbrDelAns"));
       int j = 0;
        if (nbrDelAns != 0) {
            for (int i = 0; i < choiceBtn.length; i++) {
                if (!choiceBtn[i].getText().toString().equalsIgnoreCase(q.getCorrectAnswer())) {
                    if (j != 1) {
                        j++;
                        choiceBtn[i].setBackgroundResource(R.drawable.choice_erreur_chip);
                    }
                }
            }
            nbrDelAns--;
            Toast.makeText(getApplicationContext(), "بقيت لديك " +" "+ nbrDelAns+"محولات"+" ", Toast.LENGTH_LONG).show();
            cv.put("nbrDelAns",nbrDelAns);
        //    dbw.update(choiseOfDomain.domain+"Level",cv,"_id=?",new String[]{1+""});
            dbw.update("cultureLevel",cv,"_id=?",new String[]{position+""});
        }else{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("تنبيه");
            builder.setMessage("لقد نفذت جميع المحاولات المسموح بها في هذه المرحلة");
            builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog d=builder.create();
            d.show();
        }

    }

   // public void shareWithFriend(View view) {
        /*Intent share=new Intent(Intent.ACTION_SENDTO);
        share.putExtra(Intent.EXTRA_SUBJECT,"هدا التطبيق جيد جدا");
        share.putExtra(Intent.EXTRA_TEXT,"هدا التطبيق جيدا");
        startActivity(Intent.createChooser(share,"تطبيق"));*/
    //}

    public class MyTimer extends CountDownTimer {
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            progressBar.setProgress((int)Math.round(millisUntilFinished/100d));
            //AttentPlayer.start();

        }

        @Override
        public void onFinish() {
         progressBar.setProgress(0);
         //AttentPlayer.stop();
         for(Button btn:choiceBtn){
             btn.setEnabled(false);
         }
         failPlayer.start();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("إنتباه");
        builder.setMessage(R.string.clos_confirm);
        builder.setIcon(R.drawable.close);
        builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //CompActivity.super.onBackPressed();
               // CompActivity.super.finish();
                //CompActivity.super.onStop();
                clapPlayer.stop();
               failPlayer.stop();
               t.cancel();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivityForResult(intent,2);
                finish();
            }
        });
        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog d=builder.create();
        d.show();

    }
    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}
