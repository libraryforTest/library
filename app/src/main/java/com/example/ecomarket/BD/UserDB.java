package com.example.ecomarket.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ecomarket.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDB {
    private static final String DB_NAME = "Ecomarketdb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "Users";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String USERNAME_COL = "username";

    // below variable id for our course duration column.
    private static final String EMAIL_COL = "email";

    // below variable for our course description column.
    private static final String PDP_COL = "pdp";

    // below variable is for our course tracks column.
    private static final String PWD_COL = "pwd";
    private static final String ROLE_COL = "role";
    private SQLiteDatabase bdd;
    private EcomarketDB ecomarketDB;

    public UserDB(Context context)
    {
        ecomarketDB = new EcomarketDB(context,DB_NAME,null,DB_VERSION);
    }
    public void opendb()
    {
        bdd = ecomarketDB.getWritableDatabase();
    }
    public void close()
    {
        bdd.close();
    }
    public SQLiteDatabase getBdd()
    {
        return bdd;
    }
    public User getUserById(int userId) {
        Cursor z = bdd.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=? ",
                new String[]{String.valueOf(userId)});

        if (z.moveToFirst()) {
            // Create a new User object with the data from the cursor
            User result = new User();
            result.setId(z.getString(z.getColumnIndex("id")));
            result.setUsername(z.getString(z.getColumnIndex("username")));
            result.setPwd(z.getString(z.getColumnIndex("pwd")));
            result.setRole(z.getString(z.getColumnIndex("role")));
            result.setEmail(z.getString(z.getColumnIndex("email")));
            result.setPdp(z.getString(z.getColumnIndex("pdp")));

            // Add other attributes if necessary
            return result;
        } else {
            // No user matches the criteria
            return null;
        }
    }

    public long insertUser (User user){

        ContentValues values = new ContentValues();

        values.put(USERNAME_COL, user.getUsername());
        values.put(EMAIL_COL, user.getEmail());
        values.put (PDP_COL, user.getPdp());
        values.put (PWD_COL, user.getPwd());
        values.put (ROLE_COL, user.getRole());

        Long res = bdd.insert(TABLE_NAME,  null, values);
        return res;
    }
    public User getUserWithLog(User user) {
        Cursor z = bdd.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE email=? AND pwd=?",
                new String[]{user.getEmail(), user.getPwd()});

        if (z.moveToFirst()) {
            // Create a new User object with the data from the cursor
            User result = new User();
            result.setUsername(z.getString(z.getColumnIndex("username")));
            result.setPwd(z.getString(z.getColumnIndex("pwd")));
            result.setRole(z.getString(z.getColumnIndex("role")));
            result.setEmail(z.getString(z.getColumnIndex("email")));
            result.setPdp(z.getString(z.getColumnIndex("pdp")));

            // Add other attributes if necessary
            return result;
        } else {
            // No user matches the criteria
            return null;
        }

    }
    public List<User> getAllUsers() {
        // Créer une liste vide pour stocker les utilisateurs
        List<User> users = new ArrayList<User>();
        // Exécuter la requête SQL pour sélectionner tous les utilisateurs
        Cursor z = bdd.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        // Vérifier si le curseur n'est pas vide
        if (z.moveToFirst()) {
            // Parcourir le curseur et créer des objets User pour chaque ligne
            do {
                User user = new User();
                user.setUsername(z.getString(z.getColumnIndex("username")));
                user.setPwd(z.getString(z.getColumnIndex("pwd")));
                user.setRole(z.getString(z.getColumnIndex("role")));
                user.setEmail(z.getString(z.getColumnIndex("email")));
                user.setPdp(z.getString(z.getColumnIndex("pdp")));
                // Ajouter l'utilisateur à la liste
                users.add(user);
            } while (z.moveToNext());
        }
        // Fermer le curseur
        z.close();
        // Renvoyer la liste des utilisateurs
        return users;
    }

    public int deleteUserById(int userId) {
        return bdd.delete(TABLE_NAME, ID_COL + "=?", new String[]{String.valueOf(userId)});
    }
    public int updatePasswordById(int userId, String newPassword) {
        ContentValues values = new ContentValues();
        values.put(PWD_COL, newPassword);

        return bdd.update(TABLE_NAME, values, ID_COL + "=?", new String[]{String.valueOf(userId)});
    }
    public int updateUserById(int userId, User updatedUser) {
        ContentValues values = new ContentValues();
        values.put(USERNAME_COL, updatedUser.getUsername());


        return bdd.update(TABLE_NAME, values, ID_COL + "=?", new String[]{String.valueOf(userId)});
    }

}
