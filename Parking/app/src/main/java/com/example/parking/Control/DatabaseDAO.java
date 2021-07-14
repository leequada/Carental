package com.example.parking.Control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.parking.Model.Car;
import com.example.parking.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDAO extends SQLiteOpenHelper {
    public static final String  name = "Parking.sqlite";
    public DatabaseDAO(@Nullable Context context) {
        super(context,name,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void queryData(String sql ){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    public User Checklogin(String username , String password){
        String sql = "SELECT * FROM Users WHERE account = ? and passwords = ?";
        String[] value = {username,password};
        SQLiteDatabase sqLiteDatabase =getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery(sql,value);
        while(cursor.moveToNext()){
            String id =cursor.getString(0);
            String name = cursor.getString(1);
            String account = cursor.getString(2);
            String pass = cursor.getString(3);
            User u = new User(id,name,account,pass);
            return u;
        }
        return null;
    }
    public Car checkCar(String name){
        String sql = "SELECT * FROM Car WHERE Bienxe = ?";
        String[] value = {name};
        SQLiteDatabase sqLiteDatabase =getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery(sql,value);
        while(cursor.moveToNext()){
            String id =cursor.getString(0);
            String bienxe = cursor.getString(1);
            String timein = cursor.getString(2);
            String datein = cursor.getString(3);
            Car c = new Car(id,bienxe,timein,datein);
            return c;
        }
        return null;
    }
    public Long addUser(String name, String account , String password){
        ContentValues values =new ContentValues();
        values.put("Name",name);
        values.put("account",account);
        values.put("passwords",password);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("Users",null,values);
    }
    public List<Car> SearchCarbyName(String name){
        List<Car> list = new ArrayList<>();
        String sql = "SELECT * FROM Car WHERE Bienxe LIKE '%"+name+"%' ";

        SQLiteDatabase sqLiteDatabase =getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String id =cursor.getString(0);
            String bienxe = cursor.getString(1);
            String time = cursor.getString(2);
            String datein = cursor.getString(3);
            list.add(new Car(id,bienxe,time,datein));
        }
        return list;
    }
    public Long AddCar(Car c){
        ContentValues values = new ContentValues();
        values.put("Bienxe",c.getBienxe());
        values.put("Time",c.getTime());
        values.put("dateIn",c.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("Car",null,values);
    }
    public int UpdateCar(Car c){
        ContentValues values = new ContentValues();
        values.put("Bienxe",c.getBienxe());
        values.put("Time",c.getTime());
        values.put("dateIn",c.getDate());
        String sql = "id = ?";
        String []clause = {c.getId()};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("Car",values,sql,clause);
    }
    public int DeleteCar(String bienxe){
        String clause = "Bienxe = ? ";
        String[] values = {bienxe};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("Car",clause,values);
    }
}
