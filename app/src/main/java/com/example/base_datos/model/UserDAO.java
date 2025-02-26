package com.example.base_datos.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import com.example.base_datos.entities.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UserDAO {
    //Declaración de atributos-> objetos
    private ManagerDBUser dbUser;
    private Context context;
    private View view;
    private User user;

    //Constructor
    public UserDAO(Context context, View view) {
        this.context = context;
        this.view = view;
        this.dbUser = new ManagerDBUser(context); // Inicializar la base de datos
    }

    //encapsulacion de metodo
    private void insertUser(User user){
        try {
            SQLiteDatabase sqLiteDatabase = dbUser.getWritableDatabase();
            if(sqLiteDatabase != null){
                final int STATUS=1;
                ContentValues values = new ContentValues();
                values.put("use_document" , user.getDocument());
                values.put("use_names" , user.getNames() );
                values.put("use_last_names" , user.getLastNames());
                values.put("use_user" , user.getUser());
                values.put("use_password" , user.getPassword());
                values.put("use_status" , STATUS);
                long response =  sqLiteDatabase.insert("users" , null,values);
                Snackbar.make(this.view, "Se ha registrado el usuario:"+response, Snackbar.LENGTH_LONG).show();

            }else{
                Snackbar.make(this.view, "No se puede registrar el usuario:", Snackbar.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Log.i("Error en la gestion de la BD", " "+e.getMessage().toString());
            Snackbar.make(this.view, "Error en la gestión de la BD"
                    +e.getMessage().toString() ,Snackbar.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }
    }
    //Metodo de acceso a insertuser
    public  void  getInsertUser(User user){
        insertUser(user);
    }

    //Consulta de todos los usuarios
    public ArrayList<User> getUserList(){
        try {
            SQLiteDatabase sqLiteDatabase = dbUser.getReadableDatabase();
            String query = "select * from users where use_status = 1;";
            ArrayList<User> listUsers = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            if (cursor.moveToFirst()){
                do{
                    User user1 = new User();
                    user1.setDocument(cursor.getInt(0));
                    user1.setNames(cursor.getString(1));
                    user1.setLastNames(cursor.getString(2));
                    user1.setUser(cursor.getString(3));
                    user1.setPassword(cursor.getString(4));
                    listUsers.add(user1);
                }while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
            return listUsers;
        } catch (Exception e){
            Log.i("Error en la consulta de la BD", " "+e.getMessage().toString());
            throw new RuntimeException(e);
        }
    }

    public boolean isUserExists(int documento, String usuario) {
        SQLiteDatabase db = dbUser.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM users WHERE use_document = ? AND use_user = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(documento), usuario});

        boolean exists = false;
        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0) > 0;
        }

        cursor.close();
        db.close();
        return exists;
    }

    public User getUserByDocument(int doc) {
        SQLiteDatabase db = dbUser.getReadableDatabase();
        String query = "SELECT * FROM users WHERE use_document = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(doc)});

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setDocument(cursor.getInt(0));
            user.setNames(cursor.getString(1));
            user.setLastNames(cursor.getString(2));
            user.setUser(cursor.getString(3));
            user.setPassword(cursor.getString(4));
        }

        cursor.close();
        db.close();
        return user;
    }
    public boolean updateUser(User user) {
        SQLiteDatabase db = dbUser.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("use_names", user.getNames());
        values.put("use_last_names", user.getLastNames());
        values.put("use_user", user.getUser());
        values.put("use_password", user.getPassword());

        int rows = db.update("users", values, "use_document = ?", new String[]{String.valueOf(user.getDocument())});
        db.close();

        return rows > 0;
    }

}
