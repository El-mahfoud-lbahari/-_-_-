package com.mahtiz.mansayarbah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.*;
public class MainActivity extends AppCompatActivity {
    public static MyDBHelper helper;
    public static String nameOfJoeur;
    Button startBtn,out,infOfApp;
    TextView marhaban;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "616103192399372_616104669065891", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();


        Toolbar toolBar=findViewById(R.id.toolbar2);
       setSupportActionBar(toolBar);
        helper = new MyDBHelper(getApplicationContext(), "cultureLevel", 1);
        helper = new MyDBHelper(getApplicationContext(), "sportLevel", 1);
        helper = new MyDBHelper(getApplicationContext(), "islamLevel", 1);
        helper = new MyDBHelper(getApplicationContext(), "randLevel", 1);
        AddInDB();
        startBtn=findViewById(R.id.startBtn);
        out=findViewById(R.id.out);
        infOfApp=findViewById(R.id.infOfApp);
        marhaban=findViewById(R.id.marhaban);
        startBtn.setTypeface(Typeface.createFromAsset(getAssets(),"AraHalaBoShesha-Regular.otf"));
        out.setTypeface(Typeface.createFromAsset(getAssets(),"AraHalaBoShesha-Regular.otf"));
        infOfApp.setTypeface(Typeface.createFromAsset(getAssets(),"AraHalaBoShesha-Regular.otf"));
        marhaban.setTypeface(Typeface.createFromAsset(getAssets(),"AraHalaBoShesha-Regular.otf"));
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* String name= nameEd.getText().toString();
                nameOfJoeur=name;
                if(name==null || name.trim().equalsIgnoreCase("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(" إنتباه");
                    builder.setMessage("أكتب إسمك من فضلك");
                    builder.setIcon(R.drawable.close);
                    builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }else{*/
                    Intent intent=new Intent(getApplicationContext(),ActivityLevel.class);
                    //intent.putExtra("name","mahfoud");
                    startActivity(intent);
               // }
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("إنتباه");
        builder.setMessage(R.string.clos_confirm);
        builder.setIcon(R.drawable.close);
        builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.super.onBackPressed();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compt_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.aboutMenuItem){
            Toast.makeText(getApplicationContext(),"هذا التطبيق جيد للأطفال",Toast.LENGTH_SHORT).show();
        }else if(item.getItemId()==R.id.closeMenuItem){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onActivityReenter(int resultCode, Intent data) {

        super.onActivityReenter(resultCode, data);
    }
    private void AddInDB() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from cultureLevel ", null);
        if (!(cursor.getCount() >= 1)) {
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('pass','2','2');");
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('nopass','2','2');");
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('nopass','2','2');");
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('nopass','3','3');");
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('nopass','3','3');");
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('nopass','4','4');");
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('nopass','4','4');");
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('nopass','5','5');");
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('nopass','5','5');");
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('nopass','6','6');");
            db.execSQL("insert into cultureLevel(etatLevel,nbrDelAns,nbrCall) values('nopass','6','6');");
            /////////////////////////////////////////////////////////////////////////////////
            /*cursor = db.rawQuery("select * from sportLevel ", null);
            db.execSQL("insert into sportLevel(etatLevel,nbrDelAns,nbrCall) values('pass','5','5');");
            db.execSQL("insert into sportLevel(etatLevel,nbrDelAns) values('nopass','5');");
            db.execSQL("insert into sportLevel(etatLevel,nbrDelAns) values('nopass','5');");
            db.execSQL("insert into sportLevele(tatLevel,nbrDelAns) values('nopass','5');");
            db.execSQL("insert into sportLevel(etatLevel,nbrDelAns) values('nopass','5');");
            db.execSQL("insert into sportLevel(etatLevel,nbrDelAns) values('nopass','5');");
            db.execSQL("insert into sportLevel(etatLevel,nbrDelAns) values('nopass','5');");
            db.execSQL("insert into sportLevel(etatLevel,nbrDelAns) values('nopass','5');");
            db.execSQL("insert into sportLevel(etatLevel,nbrDelAns) values('nopass','5');");
            db.execSQL("insert into sportLevel(etatLevel,nbrDelAns) values('nopass','5');");
            ////////////////////////////////////////////////////////////////////////////
            cursor = db.rawQuery("select * from islamLevel ", null);
            db.execSQL("insert into islamLevel(etatLevel,nbrDelAns,nbrCall) values('pass','5','5');");
            db.execSQL("insert into islamLevel(etatLevel) values('nopass');");
            db.execSQL("insert into islamLevel(etatLevel) values('nopass');");
            db.execSQL("insert into islamLevel(etatLevel) values('nopass');");
            db.execSQL("insert into islamLevel(etatLevel) values('nopass');");
            db.execSQL("insert into islamLevel(etatLevel) values('nopass');");
            db.execSQL("insert into islamLevel(etatLevel) values('nopass');");
            db.execSQL("insert into islamLevel(etatLevel) values('nopass');");
            db.execSQL("insert into islamLevel(etatLevel) values('nopass');");
            db.execSQL("insert into islamLevel(etatLevel) values('nopass');");
            //////////////////////////////////////////////////////////////////
            cursor = db.rawQuery("select * from awasimLevel ", null);
            db.execSQL("insert into awasimLevel(etatLevel,nbrDelAns,nbrCall) values('pass','5','5');");
            db.execSQL("insert into awasimLevel(etatLevel) values('nopass');");
            db.execSQL("insert into awasimLevel(etatLevel) values('nopass');");
            db.execSQL("insert into awasimLevel(etatLevel) values('nopass');");
            db.execSQL("insert into awasimLevel(etatLevel) values('nopass');");
            db.execSQL("insert into awasimLevel(etatLevel) values('nopass');");
            db.execSQL("insert into awasimLevel(etatLevel) values('nopass');");
            db.execSQL("insert into awasimLevel(etatLevel) values('nopass');");
            db.execSQL("insert into awasimLevel(etatLevel) values('nopass');");
            db.execSQL("insert into awasimLevel(etatLevel) values('nopass');");*/
        }
    }
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    public void infoApp(View view) {
        Intent i=new Intent(getApplicationContext(),Pdf_info.class);
        startActivity(i);
    }
}
