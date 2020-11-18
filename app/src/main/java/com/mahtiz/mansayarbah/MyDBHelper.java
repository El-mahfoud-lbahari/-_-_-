package com.mahtiz.mansayarbah;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cultureLevel (_id integer primary key autoincrement not null,etatLevel text not null,nbrDelAns real,nbrCall real)");
        db.execSQL("create table sportLevel (_id integer primary key autoincrement not null,etatLevel  text not null,nbrDelAns real,nbrCall real)");
        db.execSQL("create table islamLevel (_id integer primary key autoincrement not null,etatLevel text not null,nbrDelAns real,nbrCall real)");
        db.execSQL("create table randLevel (_id integer primary key autoincrement not null,etatLevel  text not null,nbrDelAns real,nbrCall real)");
        db.execSQL("create table awasimLevel (_id integer primary key autoincrement not null,etatLevel  text not null,nbrDelAns real,nbrCall real)");
        // db.execSQL("create table mochtarik (_id integer primary key autoincrement not null,Nom text not null,Prenom text not null,NombreCin real,NbrIchtirak real,ancienControle real,noveauControle real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists cultureLevel");
        db.execSQL("drop table if exists sportLevel");
        db.execSQL("drop table if exists islamLevel");
        db.execSQL("drop table if exists randLevel");
        db.execSQL("drop table if exists awasimLevel");

    }
}
