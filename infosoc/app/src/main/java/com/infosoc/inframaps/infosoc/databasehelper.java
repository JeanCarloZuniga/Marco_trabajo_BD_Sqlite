package com.infosoc.inframaps.infosoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databasehelper extends SQLiteOpenHelper  {
public static final String database_name = "base_infosoc.db";
public static final String table_name = "viajes";
public static final String viajes_id = "ID";
public static final String viajes_nombre = "nombre";
public static final String viajes_duracion = "duracion";

    public databasehelper(Context context) {
        super(context, database_name, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // System.out.println("entra");
        db.execSQL("create table " + table_name + " (" +viajes_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+viajes_nombre+" TEXT, "+viajes_duracion+" REAL)");
        //   System.out.println("sale");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public Boolean insertar(String nombre_trip, String duracion_trip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(viajes_nombre, nombre_trip);
        contentValues.put(viajes_duracion, duracion_trip);
        long result = db.insert(table_name, null, contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor consultarTodo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+table_name, null);
        return res;
    }

    public boolean actualizar(String id, String nombre_trip, String duracion_trip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(viajes_id, id);
        contentValues.put(viajes_nombre, nombre_trip);
        contentValues.put(viajes_duracion, duracion_trip);
        db.update(table_name, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    public Integer borrar(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name, "ID = ?", new String[]{id});
    }
}