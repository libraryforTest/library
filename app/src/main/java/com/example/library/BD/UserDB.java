package com.example.library.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.library.Model.User;

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
    public long insertUser (User user){

        ContentValues values = new ContentValues();

        values.put(USERNAME_COL, user.getUsername());
        values.put(EMAIL_COL, user.getEmail());
        values.put (PDP_COL, user.getPdp());
        values. put (PWD_COL, user.getPwd());
        values. put (ROLE_COL, user.getRole());

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


}
