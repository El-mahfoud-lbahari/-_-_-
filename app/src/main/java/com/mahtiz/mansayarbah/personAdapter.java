package com.mahtiz.mansayarbah;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import static com.mahtiz.mansayarbah.R.*;

public class personAdapter extends BaseAdapter {
    ArrayList<Levelinfo> levelInfo;
    int layoutId;
    Context context;
    int k;
    public personAdapter(Context context, ArrayList<Levelinfo> levelInfo,int layoutId,int k){
        this.context=context;
        this.layoutId=layoutId;
        this.levelInfo=levelInfo;
        this.k=k;
    }
    @Override
    public int getCount() {
        return levelInfo.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         View view= LayoutInflater.from(context).inflate(layoutId,null);
            ImageView photoId=view.findViewById(id.imageTv);
            TextView  levelTv=view.findViewById(id.levelTv);
        levelTv.setTypeface(Typeface.createFromAsset(context.getAssets(),"AraHalaBoShesha-Regular.otf"));
        Levelinfo lv=levelInfo.get(position);
            photoId.setImageResource(lv.getPhotoId());
            levelTv.setText(lv.getLevelname());
            if(position==k){
        @SuppressLint("ResourceType") Animation animate= AnimationUtils.loadAnimation(context, R.animator.animat_level_pass);
        photoId.startAnimation(animate);
            }
        return view;
    }

}
