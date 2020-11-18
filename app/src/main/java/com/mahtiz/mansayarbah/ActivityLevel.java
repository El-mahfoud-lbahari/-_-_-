package com.mahtiz.mansayarbah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.*;

import java.util.ArrayList;

public class ActivityLevel extends AppCompatActivity {
    String[] Level = new String[]{"المستوى 1", "المستوى 2", "المستوى 3", "المستوى 4", "المستوى 5","المستوى 6","المستوى 7","المستوى 8","المستوى 9","المستوى 10","المستوى 11"};
    String etatLevel;
    ListView ls;
    SQLiteDatabase db;
    Cursor cursor;
    ArrayList<Levelinfo> arrayListLevel = new ArrayList<Levelinfo>();
    Levelinfo L;
    int j, k = 0;
    private AdView adView;//= new AdView(this, "IMG_16_9_APP_INSTALL#585688515441540_585688912108167", AdSize.BANNER_HEIGHT_50);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
/*try{
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "IMG_16_9_APP_INSTALL#585688515441540_585688912108167", AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_contain);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();}catch (Exception e){

}*/

            //n=getIntent().getIntExtra("domain",1);
            //&& getIntent().hasExtra("domain")
            db = MainActivity.helper.getReadableDatabase();
            ls = findViewById(R.id.listLevel);
            String baselevel = choiseOfDomain.domain + "Level";
            for (int i = 0; i < 11; i++) {
                j = i + 1;
              //  cursor = db.rawQuery("select * from " +choiseOfDomain.domain+"Level" +" where _id=?", new String[]{j + ""});
                cursor = db.rawQuery("select * from cultureLevel where _id=?", new String[]{j + ""});

                cursor.moveToFirst();
                    etatLevel = cursor.getString(cursor.getColumnIndex("etatLevel"));
                L = new Levelinfo();
                L.setLevelname(Level[i]);
                if (etatLevel.equalsIgnoreCase("pass")) {
                    k = i;
                    L.setPhotoId(android.R.drawable.star_on);
                } else {
                    L.setPhotoId(android.R.drawable.star_off);
                }
                arrayListLevel.add(L);
            }
            personAdapter adapter = new personAdapter(getApplicationContext(), arrayListLevel, R.layout.item_lest_level, k);
            ls.setAdapter(adapter);
            // ls.setItemsCanFocus(true);
            ///////////////////////////////////////////////////////////////////////
            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), CompActivity.class);
                    int mostawa = position + 1;
                     //   cursor = db.rawQuery("select * from "+choiseOfDomain.domain+"Level"+" where _id=?", new String[]{mostawa + ""});
                    cursor = db.rawQuery("select * from cultureLevel where _id=?", new String[]{mostawa + ""});
                    cursor.moveToFirst();
                        etatLevel = cursor.getString(cursor.getColumnIndex("etatLevel"));
                        if (etatLevel.equalsIgnoreCase("pass")) {
                            //intent.putExtra("choice", choiseOfDomain.domain + mostawa);
                            intent.putExtra("choice", "culture"+ mostawa);
                            intent.putExtra("position", mostawa);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "يجب عليك إكمال المستوى السابق", Toast.LENGTH_LONG).show();
                        }
                }
            });

        }
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
