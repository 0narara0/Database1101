package com.cmy.www.database1101;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    private static final String name = "awe.db";
    private static final SQLiteDatabase.CursorFactory factory = null;
    private static final int version = 3;

    public MyDBOpenHelper(Context context) {
        super(context, name, factory, version);
    }

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE awe_country (_id INTEGER PRIMARY KEY AUTOINCREMENT, country TEXT, capital TEXT);");
//                                                                     자동 증가

        for(int i=0; i<10; i++){
            db.execSQL("INSERT INTO awe_country VALUES( null, '" + "Country"+ i + "', '" + "Capital" + i + "');");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE awe_country ;");
        db.execSQL("DROP TABLE awe_country_visitedcount;");

        db.execSQL("CREATE TABLE awe_country (pkid TEXT PRIMARY KEY, country TEXT, capital TEXT);");
        db.execSQL("CREATE TABLE awe_country_visitedcount(fkid TEXT);"); // 1:n관계라서 테이블을 두개를 만드는 것




//        Toast.makeText(this.,"onUpgrade", Toast.LENGTH_LONG).show();
    }

    public void deleteRecord(SQLiteDatabase mdb, String country) {
        mdb.execSQL("DELETE FROM awe_country WHERE country='" + country + "';");
    }

}
