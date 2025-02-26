package com.example.base_datos.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.SQLClientInfoException;

public class ManagerDBUser extends SQLiteOpenHelper {
    //Declaracion de atributos
    private static final String DATA_BASE= "dbUsers";
    private static final int VERSION = 1;
    private static final String TABLE_USERS ="users";
    private static final String QUERY_TABLE_USERS ="CREATE TABLE "+TABLE_USERS+"(use_document INTEGER PRIMARY KEY," +
            "use_names varchar(150) NOT NULL, use_last_names varchar(150) NOT NULL, use_user varchar(100)," +
            "use_password varchar(25) NOT NULL, use_status INTEGER(1));";
    //2.Constructor
    public ManagerDBUser(@Nullable Context context) {
        super(context, DATA_BASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL(QUERY_TABLE_USERS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int oldVersion, int newVersion) {
        final String DOWND_USER ="DROP TABLE IF EXISTS "+TABLE_USERS;
        dataBase.execSQL(DOWND_USER);
        onCreate(dataBase);
    }
}
