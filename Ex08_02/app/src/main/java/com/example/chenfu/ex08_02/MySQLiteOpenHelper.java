package com.example.chenfu.ex08_02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper{
    private static final String DB_Name = "BMI_DataBase";
    private static final int DB_Version = 1;
    private static final String BMI_Table="BMI_Table";
    private static final String TABLE_CREATE ="CREATE TABLE BMI_Table(" +
                                              "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                              "height REAL," +
                                              "weight REAL," +
                                              "BMI TEXT,"    +
                                              "BMI_Zoom TEXT,"+
                                              "createTime Datetime);";

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BMI_Table);
        onCreate(db);
    }

    public long insert(BMIbean bmibean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("height", bmibean.getHeight());
        values.put("weight", bmibean.getWeight());
        values.put("BMI", bmibean.setBMI());
        values.put("BMI_Zoom", bmibean.getBMI_Zoom());
        values.put("createTime",getTimeNow());
        return db.insert(BMI_Table, null, values);
    }
    public int delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?;";
        String[] whereArgs = {String.valueOf(id)};
        return db.delete(BMI_Table, whereClause, whereArgs);
    }
    public List<BMIbean> getAllData(){
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {"id","height","weight","BMI","BMI_Zoom","createTime"};
        Cursor cursor = db.query(BMI_Table, columns, null, null, null,null,null,null);

        List<BMIbean> listbmi = new ArrayList<>();
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            double Weight=cursor.getDouble(1);
            double Height=cursor.getDouble(2);
            String BMI = cursor.getString(3);
            String BMI_Zoom = cursor.getString(4);
            String createTime =cursor.getString(5);
            Log.e("sql_get","id="+id+" Weight="+Weight+" Height="+Height+" BMI="+BMI+" BMI_Zoom="+BMI_Zoom+" createTime="+createTime);
            BMIbean bmibean = new BMIbean(id,Weight,Height,BMI,BMI_Zoom,createTime);
            listbmi.add(bmibean);
        }
        cursor.close();
        db.close();
        return listbmi;
    }

    public String getTimeNow(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("MM/dd hh:mm:ss");

        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
}
