package com.cmy.www.database1101;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDBOpenHelper dbHelper;
    SQLiteDatabase mdb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDBOpenHelper(this,"awe.db",null,1);
        mdb =dbHelper.getWritableDatabase();
        Button buttonInsert = findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(this);
        Button buttonRead = findViewById(R.id.buttonRead);
        buttonRead.setOnClickListener(this);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(this);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText countryEditText = (EditText)findViewById(R.id.editTextCountry);
        EditText cityEditText = (EditText)findViewById(R.id.editTextCity);
        String country = countryEditText.getText().toString();
        String city = cityEditText.getText().toString();

        switch (v.getId()){
            case R.id.buttonInsert:
                mdb.execSQL("INSERT INTO awe_country Values(null, '" + country +"','"+city+"');");
                break;
            case R.id.buttonRead:
                TextView tvResult = (TextView)findViewById(R.id.textViewResult);
                String query = "SELECT * FROM awe_country ORDER BY _id DESC";
                Cursor cursor = mdb.rawQuery(query,null);
                String str ="";
                while (cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    country = cursor.getString(cursor.getColumnIndex("country"));
                    city = cursor.getString(cursor.getColumnIndex("capital"));
                    str+=(id+":"+country+"-"+city+"\n");
                }
                tvResult.setText(str);
                break;
            case R.id.buttonDelete:
                mdb.execSQL("DELETE FROM awe_country WHERE country = 'France'");

                break;
            case R.id.buttonUpdate:
                mdb.execSQL("UPDATE awe_country SET capital = 'Tokyo' WHERE country= 'Japan'");

                break;
        }


    }
}
