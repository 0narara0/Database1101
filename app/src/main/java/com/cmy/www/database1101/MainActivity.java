package com.cmy.www.database1101;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDBOpenHelper dbHelper;
    SQLiteDatabase mdb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDBOpenHelper(this,"awe.db",null,3);
        mdb =dbHelper.getWritableDatabase();
        Button buttonInsert = findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(this);
        Button buttonRead = findViewById(R.id.buttonRead);
        buttonRead.setOnClickListener(this);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(this);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(this);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);
        Button buttonAddVisited = findViewById(R.id.buttonAddVisited);
        buttonAddVisited.setOnClickListener(this);
        Button buttonInit = findViewById(R.id.buttonInit);
        buttonInit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText countryEditText = (EditText)findViewById(R.id.editTextCountry);
        EditText cityEditText = (EditText)findViewById(R.id.editTextCity);
        String country = countryEditText.getText().toString();
        String city = cityEditText.getText().toString();
        TextView textViewPkId = findViewById(R.id.textViewPkId);
        TextView textViewVisitedTotalCount = findViewById(R.id.textViewVisitedTotalCount);
        String strPkID;
        String query1, query2;
//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//        57행은 지선쌤 코드
//        60-61행은 민경쌤 코드 pkid 날짜시간을 String으로 받아오는 것.
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String datetime = format.format(new Date());


        switch (v.getId()){

            case R.id.buttonAddVisited:

                strPkID = textViewPkId.getText().toString();
                query1 = "INSERT INTO awe_country_visitedcount VALUES('"+strPkID+"')";
                mdb.execSQL(query1);
                //break; break를 빼면 아래로 내려가서 아래 buttonSearch가 실행된다.

            case R.id.buttonSearch:
                country = countryEditText.getText().toString();

                query2 = "SELECT pkid, country, capital, count(fkid) visitedTotal "+
                        "FROM awe_country LEFT JOIN awe_country_visitedcount " +
                        "ON pkid = fkid WHERE country ='"+country+"'";
                Cursor cursor1 = mdb.rawQuery(query2,null);


                if(cursor1.getCount()>0){
                    cursor1.moveToFirst();

                    String visitedTotal = cursor1.getString(cursor1.getColumnIndex("visitedTotal"));
                    String pkid = cursor1.getString(cursor1.getColumnIndex("pkid"));
                    String capital = cursor1.getString(cursor1.getColumnIndex("capital"));

                    textViewVisitedTotalCount.setText(visitedTotal);
                    cityEditText.setText(capital);
                    textViewPkId.setText(pkid);
                }
                break;

            case R.id.buttonInit:
                countryEditText.setText("");
                cityEditText.setText("");
                textViewPkId.setText("");
                textViewVisitedTotalCount.setText("");

            case R.id.buttonInsert:
                mdb.execSQL("INSERT INTO awe_country Values( '"+datetime+"', '" + country +"','"+city+"');");
            break;

            case R.id.buttonRead:
                TextView tvResult = (TextView)findViewById(R.id.textViewResult);
                String query = "SELECT * FROM awe_country";
//                String query = "SELECT * FROM awe_country ORDER BY _id DESC"; _id 있을 때 내림차순
                Cursor cursor = mdb.rawQuery(query,null);
                String str ="";
                while (cursor.moveToNext()){
                    String pkid = cursor.getString(0);
                    country = cursor.getString(cursor.getColumnIndex("country"));
                    city = cursor.getString(cursor.getColumnIndex("capital"));
                    str+=(pkid+":"+country+"-"+city+"\n");
                }
                tvResult.setText(str);
                break;


            case R.id.buttonDelete:
                mdb.execSQL("DELETE FROM awe_country WHERE country = '"+country+"';");
                break;

            case R.id.buttonUpdate:
                mdb.execSQL("UPDATE awe_country SET capital = '"+city+"' WHERE country= '"+country+"';");
                break;

        }


    }
}
